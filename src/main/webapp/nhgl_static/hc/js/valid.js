/* ==========================================================
 * sco.valid.js
 * http://github.com/terebentina/sco.js
 * ==========================================================
 * Copyright 2013 Dan Caragea.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================== */

/*jshint laxcomma:true, sub:true, browser:true, jquery:true, smarttabs:true */

; (function($, undefined) {
    "use strict";

    var pluginName = 'valid';

    function Valid($form, options) {
        this.options = $.extend({},
        $.fn[pluginName].defaults, options);
        this.$form = $form;
        this.allowed_rules = [];
        this.errors = {};
        
        var self = this;
        $.each(this.methods,
        function(k, v) {
            self.allowed_rules.push(k);
        });

    }

    $.extend(Valid.prototype, {
        // this is the main function - it returns either true if the validation passed or false
        validate: function() {
            var self = this,
            form_fields = this.$form.serializeArray(),
            all_fields = this.$form.find(':input[name]').map(function() {
                return this.name;
            }).get();

            // remove any possible displayed errors from previous runs
            $.each(this.errors,
            function(field_name, error) {
                var $input = self.$form.find('[name="' + field_name + '"]');
                //$input.siblings('span.ui-form-explain').html('');
                $input.siblings('.ui-tiptext-container').remove();
                if (self.options.wrapper !== null) {
                    $input.closest(self.options.wrapper).removeClass('error');
                }
            });
            this.errors = {};

            $.each(self.options.rules,
            function(field_name, rules) {
                var field = null,
                normalized_rules = {};
                // find the field in the form
                $.each(form_fields,
                function(k, v) {
                    if (v.name === field_name) {
                        field = v;
                        return false;
                    }
                });

                // if field was not found, it could mean 2 things: mispelled field name in the rules or the field is not a successful control
                // even if it's not successful we have to validate it
                if (field === null) {
                    if ($.inArray(field_name, all_fields) !== -1) {
                        field = {
                            name: field_name,
                            value: self.get_field_value(field_name)
                        };
                    }
                }

                // if it's still null then it's either mispelled or disabled. We don't care either way
                if (field !== null) {
                    $.each(rules,
                    function(rule_idx, rule_value) {
                        // determine the method to call and its args
                        // only string and objects are allowed
                        if ($.type(rule_value) === 'string') {
                            // make sure the requested method actually exists.
                            if ($.inArray(rule_value, self.allowed_rules) !== -1) {
                                normalized_rules[rule_value] = null;
                            }
                        } else {
                            // if not string then we assume it's a {key: val} object. Only 1 key is allowed
                            $.each(rule_value,
                            function(k, v) {
                                // make sure the requested method actually exists.
                                if ($.inArray(k, self.allowed_rules) !== -1) {
                                    normalized_rules[k] = v;
                                    return false;
                                }
                            });
                        }
                    });
                    $.each(normalized_rules,
                    function(fn_name, fn_args) {
                        // call the method with the requested args
                        if (self.methods[fn_name].call(self, field.name, field.value, fn_args, normalized_rules) !== true) {
                            self.errors[field.name] = self.format.call(self, field.name, fn_name, fn_args);
                        }
                    });
                }
            });
            /* 增加自定义验证规则 2013.9.13 */
            var myRules = self.options.myRules;
            if (myRules && $.isFunction(myRules) && !$.isEmptyObject(myRules())) {
                var _rules = myRules();
                $.each(_rules, function (field_name, rule) {
                    self.errors[field_name] = rule;
                })
            }
            if (!$.isEmptyObject(this.errors)) {
                this.show(this.errors);
                return false;
            } else {
                return true;
            }
        },

        show: function(errors) {
            var self = this;
            $.each(errors,
            function(k, v) {
                var $input = self.$form.find('[name="' + k + '"]'),
                $span = {},
                inputSet = $input.position(),
                errorWidth = $input.width(),
                errorLeft = inputSet.left,
                errorTop = inputSet.top + $input.height() + 10;
                if (self.options.wrapper !== null) {
                    $input.closest(self.options.wrapper).addClass('error');
                }
                if (v == self.messages.not_empty) {
                    var str = '';
                    if ($input.prev().length > 0 && $input.prev().is('label')) {
                        str = $.trim($input.prev().text());
                    } else {
                        str = $.trim($input.attr('customName'));
                    }
                    if (str.length > 0) {
                        str = (str.charAt(0) == '*' ? str.substr(1) : str);
                        var len = str.length -1,
                        s = str.charAt(len);
                        str = ((s == '：' || s == ':') ? str.substr(0, len) : str);
                        v = str + v;
                    }
                }
                //if ($span.length === 0) {
                    $span = $('<div class="ui-tiptext-container ui-tiptext-container-error"><div class="ui-tiptext-arrow ui-tiptext-arrowup"><em>◆</em><span>◆</span></div><p class="ui-tiptext ui-tiptext-error"><i class="iconfont icon-remove-sign" title="提示"></i>' + v + '</p></div>');
                    $input.after($span);
                    $span.css({
                        left: errorLeft,
                        top: errorTop,
                        width: errorWidth,
                        position: 'absolute',
                        padding: '5px 9px',
                        zIndex: 1
                    })
                //}
                //$span.html(v);
            });
        },

        methods: {
            not_empty: function(field, value) {
                return value !== null && $.trim(value).length > 0;
            },

            min_length : function(field, value, min_len,all_rules) {
                var regex1 = /[\u4E00-\u9FA5\uf900-\ufa2d]/ig;//中文字符
                var regex2 = /[^\u4E00-\u9FA5\uf900-\ufa2d]/g;//非中文字符
                var temp1 = value.replace(regex2, '');//纯中文字符串
                var temp2 = value.replace(regex1, '');//纯非中文文字符串
                        
                var length = temp1.length + temp2.length, 
                value = (length >= min_len);
                
                if (!all_rules['not_empty']) {
                    value = value || length === 0;
                }
                return value;
            },

            max_length : function(field, value, max_len) {
                var regex1 = /[\u4E00-\u9FA5\uf900-\ufa2d]/ig;//中文字符
                var regex2 = /[^\u4E00-\u9FA5\uf900-\ufa2d]/g;//非中文字符
                var temp1 = value.replace(regex2, '');//纯中文字符串
                var temp2 = value.replace(regex1, '');//纯非中文文字符串
                return temp1.length + temp2.length <= max_len;
            },

            regex: function(field, value, regexp) {
                return regexp.test(value);
            },

            email: function(field, value) {
                // by Scott Gonzalez: http://projects.scottsplayground.com/email_address_validation/
                var regex = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i;
                return regex.test($.trim(value));
            },

            url: function(field, value) {
                // by Scott Gonzalez: http://projects.scottsplayground.com/iri/
                var regex = /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i;
                return regex.test(value);
            },

            exact_length: function(field, value, exact_length, all_rules) {
                var length = $.trim(value).length,
                result = (length === exact_length);
                if (!all_rules['not_empty']) {
                    result = result || length === 0;
                }
                return result;
            },

            equals: function(field, value, target) {
                return value === target;
            },

            ip: function(field, value) {
                var regex = /^((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\.){3}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})$/i;
                return regex.test($.trim(value));
            },

            credit_card: function(field, value) {
                // accept only spaces, digits and dashes
                if (/[^0-9 \-]+/.test(value)) {
                    return false;
                }
                var nCheck = 0,
                nDigit = 0,
                bEven = false;

                value = value.replace(/\D/g, "");

                for (var n = value.length - 1; n >= 0; n--) {
                    var cDigit = value.charAt(n);
                    nDigit = parseInt(cDigit, 10);
                    if (bEven) {
                        if ((nDigit *= 2) > 9) {
                            nDigit -= 9;
                        }
                    }
                    nCheck += nDigit;
                    bEven = !bEven;
                }

                return (nCheck % 10) === 0;
            },

            alpha: function(field, value) {
                var regex = /^[a-z]*$/i;
                return regex.test(value);
            },

            alpha_numeric: function(field, value) {
                var regex = /^[a-z0-9]*$/i;
                return regex.test(value);
            },

            alpha_dash: function(field, value) {
                var regex = /^[a-z0-9_\-]*$/i;
                return regex.test(value);
            },

            digit: function(field, value) {
                var regex = /^\d*$/;
                return regex.test(value);
            },

            numeric: function(field, value) {
                var regex = /^([\+\-]?[0-9]+(\.[0-9]+)?)?$/;
                return regex.test(value);
            },

            plusNumeric: function(field, value) {
                var regex = /^([\+]?[0-9]+(\.[0-9]+)?)?$/;
                return regex.test(value);
            },


            matches: function(field, value, param) {
                return value === this.$form.find('[name="' + param + '"]').val();
            },

            money: function(field, value) {
                //var regex=/^[1-9]{1}\d*(\.\d{1,2})?$/;
                var regex = /(^[-+]?[1-9]\d*(\.\d{1,2})?$)|(^[-+]?[0]{1}(\.\d{1,2})?$)/;
                return regex.test(value);
            },

            bigger: function(field, value, param) {
                var value = parseInt(value);
                return value > this.$form.find('[name="' + param + '"]').val();
            },

            smaller: function(field, value, param) {
                var value = parseInt(value);
                return value < this.$form.find('[name="' + param + '"]').val();
            },

            chinese: function(field, value) {
                var regex = /[\u4E00-\u9FA5\uf900-\ufa2d]/ig;
                return regex.test(value);
            }
        },

        messages: {
            not_empty: '不能为空。',
            min_length: '请至少输入 :value 个字符。',
            max_length: '请最多输入 :value 个字符。',
            regex: '',
            email: '请填写正确的 email 地址。',
            url: '请填写正确的 url。',
            exact_length: '请输入 :value 字符。',
            equals: '请输入指定字符串。',
            ip: '请输入正确的 IP 地址。',
            credit_card: '请填写正确的卡号。',
            alpha: '必须输入字母。',
            alpha_numeric: '必须输入字母或数字。',
            alpha_dash: '必须输入字母、数字、下划线或连字符。',
            digit: '请输入数字。',
            numeric: '请输入十进制数字。',
            plusNumeric: '请输入正数',
            matches: '必须与前一个值相同。',
            money: '请输入金额',
            bigger:'必须比前一个值大',
            smaller:'必须比后一个值小',
            chinese: '请输入中文'
        },

        /**
		 * finds the most specific error message string and replaces any ":value" substring with the actual value
		 */
        format: function(field_name, rule, params) {
            var message;
            if (typeof this.options.messages[field_name] !== 'undefined' && typeof this.options.messages[field_name][rule] !== 'undefined') {
                message = this.options.messages[field_name][rule];
            } else {
                message = this.messages[rule];
            }

            if ($.type(params) !== 'undefined' && params !== null) {
                if ($.type(params) === 'boolean' || $.type(params) === 'string' || $.type(params) === 'number') {
                    params = {
                        value: params
                    };
                }
                $.each(params,
                function(k, v) {
                    message = message.replace(new RegExp(':' + k, 'ig'), v);
                });
            }
            return message;
        },

        /**
		 * get a normalized value for a form field.
		 */
        get_field_value: function(field_name) {
            var $input = this.$form.find('[name="' + field_name + '"]');
            if ($input.is('[type="checkbox"], [type="radio"]')) {
                return $input.is(':checked') ? $input.val() : null;
            } else {
                return $input.val();
            }
        }
    });

    /**
	 * main function to use on a form (like $('#form).scojs_valid({...})). Performs validation of the form, sets the error messages on form inputs and returns
	 * true/false depending on whether the form passed validation or not
	 *
	 * @param hash/string options the hash of rules and messages to validate the form against (and messages to show if failed validation) or the string "option"
	 * @param {string} key the option key to retrieve or set. If the third param of the function is available then act as a setter, otherwise as a getter.
	 * @param {mixed} value the value to set on the key
	 */
    $.fn[pluginName] = function(options, key, value) {
        var $form = this.eq(0),
        validator = $form.data(pluginName);

        $('input, select, textarea', $form).each(function(index){
            $(this).focus(function(){
                if ($(this).next().hasClass('ui-tiptext-container')) {
                    $(this).next().remove();
                }
            });
        })

        var formatMessage = function (message, status) {
            if (status == 'ok') {
                return '<div class="ui-message-content"><p class="ui-tiptext ui-tiptext-success"><i class="iconfont icon-ok-sign"></i>成功。</p><p class="ui-message-text">' + message + '</p></div>';
            } else {
                return '<div class="ui-message-content"><p class="ui-tiptext ui-tiptext-error"><i class="iconfont icon-remove-sign"></i>失败。</p><p class="ui-message-text">' + message + '</p></div><div class="ui-message-btn"><button class="btn" type="button">确定</button></div>';
            }
        }
        
        if ($.type(options) === 'object') {
            if (!validator) {
                var opts = $.extend({},
                $.fn[pluginName].defaults, options, $form.data());
                validator = new Valid($form, opts);
                $form.data(pluginName, validator).attr('novalidate', 'novalidate');
            }
            $form.ajaxForm({
                beforeSerialize: function() {
                    if (typeof validator.options.onBeforeValidate === 'function') {
                        validator.options.onBeforeValidate.call(validator);
                    }
                },
                beforeSubmit: function() {
                    var r = validator.validate();
                    if (r) {
                        $('[type=submit]',$form).attr('disabled',true);
                        $('[type=submit]',$form).addClass('disabled');
                    }
                    return r;
                },
                dataType: 'json',
                error: function(xhr, status, error) {
                     $('[type=submit]',$form).attr('disabled',false);
                     $('[type=submit]',$form).removeClass('disabled');
                    var response = $.parseJSON(xhr.responseText);
                    if (response && response.error) {
                        response = response.error;
                    }
                    $.message(formatMessage(response.message), $.message.TYPE_ERROR, 4, false, true);
                },
                success: function(response, status, xhr, $form) {
                    $('[type=submit]',$form).attr('disabled',false);
                    $('[type=submit]',$form).removeClass('disabled');
                    if (response.status === 'fail') {
                        if (typeof options.onFail !== 'function' || options.onFail.call(this, response, validator, $form) !== false) {
                            validator.show(response.data.errors);
                        }
                    } else if (response.status === 'error') {
                        if (typeof options.onError !== 'function' || options.onError.call(this, response, validator, $form) !== false) {
                            //$.message(response.message, $.message.TYPE_ERROR);
                            $.message(formatMessage(response.message), $.message.TYPE_ERROR, 4, false, true);
                        }
                    } else if (response.status === 'success') {
                        if (typeof options.onSuccess !== 'function' || options.onSuccess.call(this, response, validator, $form) !== false) {
                            if (response.data.next) {
                                if (response.data.next === '.') { // refresh current page
                                    window.location.href = window.location.href.replace(/#.*$/, '');
                                } else if (response.data.next === 'x') { // close the parent modal
                                    //$form.closest('.modal').trigger('close');
                                    var api = frameElement.api,
                                    W;
                                    if (api) {
                                        W = api.opener;
                                        W.dialog.focus.close();
                                    } else {
                                        dialog.focus && dialog.focus.close();
                                    }
                                } else {
                                    window.location.href = response.data.next;
                                }
                            }
                            if (response.data.message) {
                                //$.message(response.data.message, $.message.TYPE_OK);
                                $.message(formatMessage(response.data.message, 'ok'), $.message.TYPE_OK, 4, true, true);
                            }
                        }
                    }
                }
            });
            // allow chaining
            return this;
        } else if (options === 'option') {
            if ($.type(value) === 'undefined') {
                return validator.options[key];
            } else {
                validator.options[key] = value;
                return validator;
            }
        } else {
            return validator ? validator: this;
        }
    };

    $[pluginName] = function(form, options) {
        if (typeof form === 'string') {
            form = $(form);
        }
        return new Valid(form, options);
    };

    $.fn[pluginName].defaults = {
        wrapper: 'label' // the html tag that wraps the field and which defines a "row" of the form
        ,
        rules: {} // array of rules to check the form against. Each value should be either a string with the name of the method to use as rule or a hash like {method: <method params>}
        ,
        messages: {} // custom error messages like {username: {not_empty: 'hey you forgot to enter your username', min_length: 'come on, more than 2 chars, ok?'}, password: {....}}
    };
})($ || jQuery);;/* ==========================================================
 * sco.valid.js
 * http://github.com/terebentina/sco.js
 * ==========================================================
 * Copyright 2013 Dan Caragea.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================== */

/*jshint laxcomma:true, sub:true, browser:true, jquery:true, smarttabs:true */

;
(function($, undefined) {
    "use strict";

    var pluginName = 'valid';

    function Valid($form, options) {
        this.options = $.extend({},
            $.fn[pluginName].defaults, options);
        this.$form = $form;
        this.allowed_rules = [];
        this.errors = {};

        var self = this;
        $.each(this.methods,
            function(k, v) {
                self.allowed_rules.push(k);
            });

    }

    $.extend(Valid.prototype, {
        // this is the main function - it returns either true if the validation passed or false
        validate: function() {
            var self = this,
                form_fields = this.$form.serializeArray(),
                all_fields = this.$form.find(':input[name]').map(function() {
                    return this.name;
                }).get();

            // remove any possible displayed errors from previous runs
            $.each(this.errors,
                function(field_name, error) {
                    var $input = self.$form.find('[name="' + field_name + '"]');
                    //$input.siblings('span.ui-form-explain').html('');
                    $input.siblings('.ui-tiptext-container').remove();
                    if (self.options.wrapper !== null) {
                        $input.closest(self.options.wrapper).removeClass('error');
                    }
                });
            this.errors = {};

            $.each(self.options.rules,
                function(field_name, rules) {
                    var field = null,
                        normalized_rules = {};
                    // find the field in the form
                    $.each(form_fields,
                        function(k, v) {
                            if (v.name === field_name) {
                                field = v;
                                return false;
                            }
                        });

                    // if field was not found, it could mean 2 things: mispelled field name in the rules or the field is not a successful control
                    // even if it's not successful we have to validate it
                    if (field === null) {
                        if ($.inArray(field_name, all_fields) !== -1) {
                            field = {
                                name: field_name,
                                value: self.get_field_value(field_name)
                            };
                        }
                    }

                    // if it's still null then it's either mispelled or disabled. We don't care either way
                    if (field !== null) {
                        $.each(rules,
                            function(rule_idx, rule_value) {
                                // determine the method to call and its args
                                // only string and objects are allowed
                                if ($.type(rule_value) === 'string') {
                                    // make sure the requested method actually exists.
                                    if ($.inArray(rule_value, self.allowed_rules) !== -1) {
                                        normalized_rules[rule_value] = null;
                                    }
                                } else {
                                    // if not string then we assume it's a {key: val} object. Only 1 key is allowed
                                    $.each(rule_value,
                                        function(k, v) {
                                            // make sure the requested method actually exists.
                                            if ($.inArray(k, self.allowed_rules) !== -1) {
                                                normalized_rules[k] = v;
                                                return false;
                                            }
                                        });
                                }
                            });
                        $.each(normalized_rules,
                            function(fn_name, fn_args) {
                                // call the method with the requested args
                                if (self.methods[fn_name].call(self, field.name, field.value, fn_args, normalized_rules) !== true) {
                                    self.errors[field.name] = self.format.call(self, field.name, fn_name, fn_args);
                                }
                            });
                    }
                });
            /* 增加自定义验证规则 2013.9.13 */
            var myRules = self.options.myRules;
            if (myRules && $.isFunction(myRules) && !$.isEmptyObject(myRules())) {
                var _rules = myRules();
                $.each(_rules, function(field_name, rule) {
                    self.errors[field_name] = rule;
                })
            }
            if (!$.isEmptyObject(this.errors)) {
                this.show(this.errors);
                return false;
            } else {
                return true;
            }
        },

        show: function(errors) {
            var self = this;
            $.each(errors,
                function(k, v) {
                    var $input = self.$form.find('[name="' + k + '"]'),
                        $span = {},
                        inputSet = $input.position(),
                        errorWidth = $input.width(),
                        errorLeft = inputSet.left,
                        errorTop = inputSet.top + $input.height() + 10;
                    if (self.options.wrapper !== null) {
                        $input.closest(self.options.wrapper).addClass('error');
                    }
                    if (v == self.messages.not_empty) {
                        var str = '';
                        if ($input.prev().length > 0 && $input.prev().is('label')) {
                            str = $.trim($input.prev().text());
                        } else {
                            str = $.trim($input.attr('customName'));
                        }
                        if (str.length > 0) {
                            str = (str.charAt(0) == '*' ? str.substr(1) : str);
                            var len = str.length - 1,
                                s = str.charAt(len);
                            str = ((s == '：' || s == ':') ? str.substr(0, len) : str);
                            v = str + v;
                        }
                    }
                    //if ($span.length === 0) {
                    $span = $('<div class="ui-tiptext-container ui-tiptext-container-error"><div class="ui-tiptext-arrow ui-tiptext-arrowup"><em>◆</em><span>◆</span></div><p class="ui-tiptext ui-tiptext-error"><i class="iconfont icon-remove-sign" title="提示"></i>' + v + '</p></div>');
                    $input.after($span);
                    $span.css({
                        left: errorLeft,
                        top: errorTop,
                        width: errorWidth,
                        position: 'absolute',
                        padding: '5px 9px',
                        zIndex: 1
                    })
                    //}
                    //$span.html(v);
                });
        },

        methods: {
            not_empty: function(field, value) {
                return value !== null && $.trim(value).length > 0;
            },

            min_length: function(field, value, min_len, all_rules) {
                var regex1 = /[\u4E00-\u9FA5\uf900-\ufa2d]/ig; //中文字符
                var regex2 = /[^\u4E00-\u9FA5\uf900-\ufa2d]/g; //非中文字符
                var temp1 = value.replace(regex2, ''); //纯中文字符串
                var temp2 = value.replace(regex1, ''); //纯非中文文字符串

                var length = 2 * temp1.length + temp2.length,
                    value = (length >= min_len);

                if (!all_rules['not_empty']) {
                    value = value || length === 0;
                }
                return value;
            },

            max_length: function(field, value, max_len) {
                var regex1 = /[\u4E00-\u9FA5\uf900-\ufa2d]/ig; //中文字符
                var regex2 = /[^\u4E00-\u9FA5\uf900-\ufa2d]/g; //非中文字符
                var temp1 = value.replace(regex2, ''); //纯中文字符串
                var temp2 = value.replace(regex1, ''); //纯非中文文字符串
                return 2 * temp1.length + temp2.length <= max_len;
            },

            regex: function(field, value, regexp) {
                return regexp.test(value);
            },

            email: function(field, value) {
                // by Scott Gonzalez: http://projects.scottsplayground.com/email_address_validation/
                var regex = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i;
                return regex.test($.trim(value));
            },

            url: function(field, value) {
                // by Scott Gonzalez: http://projects.scottsplayground.com/iri/
                var regex = /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i;
                return regex.test(value);
            },

            exact_length: function(field, value, exact_length, all_rules) {
                var length = $.trim(value).length,
                    result = (length === exact_length);
                if (!all_rules['not_empty']) {
                    result = result || length === 0;
                }
                return result;
            },

            equals: function(field, value, target) {
                return value === target;
            },

            ip: function(field, value) {
                var regex = /^((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\.){3}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})$/i;
                return regex.test($.trim(value));
            },

            credit_card: function(field, value) {
                // accept only spaces, digits and dashes
                if (/[^0-9 \-]+/.test(value)) {
                    return false;
                }
                var nCheck = 0,
                    nDigit = 0,
                    bEven = false;

                value = value.replace(/\D/g, "");

                for (var n = value.length - 1; n >= 0; n--) {
                    var cDigit = value.charAt(n);
                    nDigit = parseInt(cDigit, 10);
                    if (bEven) {
                        if ((nDigit *= 2) > 9) {
                            nDigit -= 9;
                        }
                    }
                    nCheck += nDigit;
                    bEven = !bEven;
                }

                return (nCheck % 10) === 0;
            },

            alpha: function(field, value) {
                var regex = /^[a-z]*$/i;
                return regex.test(value);
            },

            alpha_numeric: function(field, value) {
                var regex = /^[a-z0-9]*$/i;
                return regex.test(value);
            },

            alpha_dash: function(field, value) {
                var regex = /^[a-z0-9_\-]*$/i;
                return regex.test(value);
            },

            digit: function(field, value) {
                var regex = /^\d*$/;
                return regex.test(value);
            },

            numeric: function(field, value) {
                var regex = /^([\+\-]?[0-9]+(\.[0-9]+)?)?$/;
                return regex.test(value);
            },

            plusNumeric: function(field, value) {
                var regex = /^([\+]?[0-9]+(\.[0-9]+)?)?$/;
                return regex.test(value);
            },

            plusInteger: function(field, value) {
                var regex = /^\d+$/;
                return regex.test(value);
            },

            matches: function(field, value, param) {
                return value === this.$form.find('[name="' + param + '"]').val();
            },

            money: function(field, value) {
                //var regex=/^[1-9]{1}\d*(\.\d{1,2})?$/;
                var regex = /(^[-+]?[1-9]\d*(\.\d{1,2})?$)|(^[-+]?[0]{1}(\.\d{1,2})?$)/;
                return regex.test(value);
            },

            bigger: function(field, value, param) {
                var value = parseInt(value);
                return value > this.$form.find('[name="' + param + '"]').val();
            },

            smaller: function(field, value, param) {
                var value = parseInt(value);
                return value < this.$form.find('[name="' + param + '"]').val();
            },

            chinese: function(field, value) {
                var regex = /[\u4E00-\u9FA5\uf900-\ufa2d]/ig;
                return regex.test(value);
            }
        },

        messages: {
            not_empty: '不能为空。',
            min_length: '请至少输入 :value 个字符。',
            max_length: '您输入的字符过长。',
            regex: '不符合规则。',
            email: '请填写正确的 email 地址。',
            url: '请填写正确的 url。',
            exact_length: '请输入 :value 字符。',
            equals: '请输入指定字符串。',
            ip: '请输入正确的 IP 地址。',
            credit_card: '请填写正确的卡号。',
            alpha: '必须输入字母。',
            alpha_numeric: '必须输入字母或数字。',
            alpha_dash: '必须输入字母、数字、下划线或连字符。',
            digit: '请输入数字。',
            numeric: '请输入十进制数字。',
            plusNumeric: '请输入正数',
            plusInteger: '请输入正整数',
            matches: '必须与前一个值相同。',
            money: '请输入金额',
            bigger: '必须比前一个值大',
            smaller: '必须比后一个值小',
            chinese: '请输入中文'
        },

        /**
         * finds the most specific error message string and replaces any ":value" substring with the actual value
         */
        format: function(field_name, rule, params) {
            var message,
                self = this;
            if (typeof self.options.messages[rule] !== 'undefined') {
                message = self.options.messages[rule];
            } else {
                message = self.messages[rule];
            }

            if ($.type(params) !== 'undefined' && params !== null) {
                if ($.type(params) === 'boolean' || $.type(params) === 'string' || $.type(params) === 'number') {
                    params = {
                        value: params
                    };
                }
                $.each(params,
                    function(k, v) {
                        message = message.replace(new RegExp(':' + k, 'ig'), v);
                    });
            }
            return message;
        },

        /**
         * get a normalized value for a form field.
         */
        get_field_value: function(field_name) {
            var $input = this.$form.find('[name="' + field_name + '"]');
            if ($input.is('[type="checkbox"], [type="radio"]')) {
                return $input.is(':checked') ? $input.val() : null;
            } else {
                return $input.val();
            }
        }
    });

    /**
     * main function to use on a form (like $('#form).scojs_valid({...})). Performs validation of the form, sets the error messages on form inputs and returns
     * true/false depending on whether the form passed validation or not
     *
     * @param hash/string options the hash of rules and messages to validate the form against (and messages to show if failed validation) or the string "option"
     * @param {string} key the option key to retrieve or set. If the third param of the function is available then act as a setter, otherwise as a getter.
     * @param {mixed} value the value to set on the key
     */
    $.fn[pluginName] = function(options, key, value) {
        var $form = this.eq(0),
            validator = $form.data(pluginName);

        $('input, select, textarea', $form).each(function(index) {
            $(this).focus(function() {
                if ($(this).next().hasClass('ui-tiptext-container')) {
                    $(this).next().remove();
                }
            });
        })

        var formatMessage = function(message, status) {
            if (status == 'ok') {
                return '<div class="ui-message-content"><p class="ui-tiptext ui-tiptext-success"><i class="iconfont icon-ok-sign"></i>成功。</p><p class="ui-message-text">' + message + '</p></div>';
            } else {
                return '<div class="ui-message-content"><p class="ui-tiptext ui-tiptext-error"><i class="iconfont icon-remove-sign"></i>失败。</p><p class="ui-message-text">' + message + '</p></div><div class="ui-message-btn"><button class="btn" type="button">确定</button></div>';
            }
        }

        if ($.type(options) === 'object') {
            if (!validator) {
                var opts = $.extend({},
                    $.fn[pluginName].defaults, options, $form.data());
                validator = new Valid($form, opts);
                $form.data(pluginName, validator).attr('novalidate', 'novalidate');
            }
            $form.ajaxForm({
                beforeSerialize: function() {
                    if (typeof validator.options.onBeforeValidate === 'function') {
                        validator.options.onBeforeValidate.call(validator);
                    }
                },
                beforeSubmit: function() {
                    var r = validator.validate();
                    if (r) {
                        $('[type=submit]', $form).attr('disabled', true);
                        $('[type=submit]', $form).addClass('disabled');
                    }
                    return r;
                },
                dataType: 'json',
                error: function(xhr, status, error) {
                    $('[type=submit]', $form).attr('disabled', false);
                    $('[type=submit]', $form).removeClass('disabled');
                    var response = $.parseJSON(xhr.responseText);
                    if (response && response.error) {
                        response = response.error;
                    }
                    $.message(formatMessage(response.message), $.message.TYPE_ERROR, 4, false, true);
                },
                success: function(response, status, xhr, $form) {
                    $('[type=submit]', $form).attr('disabled', false);
                    $('[type=submit]', $form).removeClass('disabled');
                    if (response.status === 'fail') {
                        if (typeof options.onFail !== 'function' || options.onFail.call(this, response, validator, $form) !== false) {
                            validator.show(response.data.errors);
                        }
                    } else if (response.status === 'error') {
                        if (typeof options.onError !== 'function' || options.onError.call(this, response, validator, $form) !== false) {
                            //$.message(response.message, $.message.TYPE_ERROR);
                            $.message(formatMessage(response.message), $.message.TYPE_ERROR, 4, false, true);
                        }
                    } else if (response.status === 'success') {
                        if (typeof options.onSuccess !== 'function' || options.onSuccess.call(this, response, validator, $form) !== false) {
                            if (response.data.next) {
                                if (response.data.next === '.') { // refresh current page
                                    window.location.href = window.location.href.replace(/#.*$/, '');
                                } else if (response.data.next === 'x') { // close the parent modal
                                    //$form.closest('.modal').trigger('close');
                                    var api = frameElement.api,
                                        W;
                                    if (api) {
                                        W = api.opener;
                                        W.dialog.focus.close();
                                    } else {
                                        dialog.focus && dialog.focus.close();
                                    }
                                } else {
                                    window.location.href = response.data.next;
                                }
                            }
                            if (response.data.message) {
                                //$.message(response.data.message, $.message.TYPE_OK);
                                $.message(formatMessage(response.data.message, 'ok'), $.message.TYPE_OK, 4, true, true);
                            }
                        }
                    }
                }
            });
            // allow chaining
            return this;
        } else if (options === 'option') {
            if ($.type(value) === 'undefined') {
                return validator.options[key];
            } else {
                validator.options[key] = value;
                return validator;
            }
        } else {
            return validator ? validator : this;
        }
    };

    $[pluginName] = function(form, options) {
        if (typeof form === 'string') {
            form = $(form);
        }
        return new Valid(form, options);
    };

    $.fn[pluginName].defaults = {
        wrapper: 'label' // the html tag that wraps the field and which defines a "row" of the form
        ,
        rules: {} // array of rules to check the form against. Each value should be either a string with the name of the method to use as rule or a hash like {method: <method params>}
        ,
        messages: {} // custom error messages like {username: {not_empty: 'hey you forgot to enter your username', min_length: 'come on, more than 2 chars, ok?'}, password: {....}}
    };
})($ || jQuery);