(function($) {
    var defaults = {
        vertical: false,
        width: 160,
        groupWidth: 160,
        zIndex: 9999,
        hideArrow: false,
        menuItemSelector: 'li',
        menuGroupSelector: 'ul',
        rootClass: 'ui-menu',
        menuItemClass: 'ui-menu-item',
        menuGroupClass: 'ui-menu-group',
        verticalClass: 'ui-menu-vertical',
        horizontalClass: 'ui-menu-horizontal',
        hasVerticalClass: 'ui-menu-has-vertical',
        hasHorizontalClass: 'ui-menu-has-horizontal',
        hoverClass: 'ui-menu-hover',
        showDuration: 0,
        hideDuration: 0,
        hideDelayDuration: 0
    };
    var _hash = window.location.hash;
    var userAgent = navigator.userAgent;
    var userAgentIndex = userAgent.indexOf("MSIE");
    function menu() {
        var option = (typeof(arguments[0]) != 'string') ? $.extend(defaults, arguments[0]) : $.extend(defaults, {});

        // Horizontal:
        // ul.ui-menu-group,ui-menu-horizontal
        //   > li.ui-menu-item,ui-menu-has-vertical
        //     > ul.ui-menu-group,ui-menu-vertical
        //       > li.ui-menu-item,ui-menu-has-horizontal
        //        > ....
        //
        // Vertical
        // ul.ui-menu-group,ui-menu-vertical
        //   > li.ui-menu-item,ui-menu-has-horizontal
        //     > ul.ui-menu-group,ui-menu-horizontal
        //       > li.ui-menu-item,ui-menu-has-vertical
        //        > ....
        var topMenuGroupClass = (option.vertical) ? option.verticalClass : option.horizontalClass,
            $menu = $(this).addClass(option.rootClass + ' ' + option.menuGroupClass + ' ' + topMenuGroupClass),
            $menuItems = $menu.find(option.menuItemSelector).addClass(option.menuItemClass),
            $menuGroups = $menu.find(option.menuGroupSelector).addClass(option.menuGroupClass);

        $('ul.ui-menu > li').addClass('ui-menu-li');
        $('ul.ui-menu > li:first').addClass('current');
        $('ul.ui-menu > li > a').addClass('ui-menu-link');

        $menuItems.hover(
            function(e) {
                //if (!$(this).hasClass('ui-menu-li')) {
                $(this).addClass(option.hoverClass);
                //}
            },
            function(e) {
                //if (!$(this).hasClass('ui-menu-li')) {
                $(this).removeClass(option.hoverClass);
                //}
            }
        );

        $menuItems.each(function() {
            if ($(this).find('a').text() == 'line') {
                $(this).addClass('line');
            }
        });
        $menuGroups.parent().each(function(index) {
            var $parentMenuItem = $(this); // menu item that has menu group
            var displayDirection = ($parentMenuItem.parent().hasClass(option.horizontalClass)) ? 'bottom' : 'right';
            $parentMenuItem.addClass((displayDirection == 'bottom') ? option.hasVerticalClass : option.hasHorizontalClass);
            if (!option.hideArrow) {
                $parentMenuItem.append((displayDirection == 'bottom') ? '<i class="iconfont icon-caret-down"></i>' : '<i class="iconfont icon-caret-right"></i>');
            }
            var $menuGroup = $parentMenuItem.find(option.menuGroupSelector + ':first').addClass(option.verticalClass);
            $parentMenuItem.hover(
                function(e) {
                    var offset = {
                        left: '',
                        top: ''
                    };
                    if (displayDirection == 'bottom') {
                        offset.left = 0;
                    } else {
                        offset.left = $(this).width() + 'px';
                        offset.top = '0px';
                    }
                    //$menuGroup.css(offset).fadeIn(option.showDuration);
                    $menuGroup.css(offset).show();
                },
                function(e) {
                    /*if (option.hideDelayDuration > 0) {
                        $menuGroup.delay(option.hideDelayDuration).fadeOut(option.hideDuration);
                    } else {*/
                    //$menuGroup.fadeOut(option.hideDuration);
                    $menuGroup.hide();
                    //}
                }
            );
        });
        /*$(document).click(function() {
            $menu.find('li').removeClass('ui-menu-hover');
            $menu.find(option.menuGroupSelector).hide();
        });*/
        /*$menu.find('li.ui-menu-li').mouseover(function() {
            var $li = $(this);
            var $siblingsLi = $li.siblings(),
                $menuGroup = $li.find(option.menuGroupSelector + ':first');
            var $siblingsMenuGroup = $siblingsLi.find(option.menuGroupSelector + ':first');
            $siblingsLi.removeClass('ui-menu-hover');
            $li.addClass('ui-menu-hover');
            //$siblingsMenuGroup.fadeOut(option.showDuration);
            $siblingsMenuGroup.hide();
            //$menuGroup.fadeIn(option.showDuration);
            $menuGroup.show();
        })*/
        $menu.find('li').click(function() {
            var that = $(this);
            if (!(that.children('ul').length > 0)) {
                //$menuGroups.fadeOut(option.hideDuration);
                $menuGroups.hide();
            }
            if(that.hasClass('ui-menu-li')){
                that.addClass('current');
                that.siblings().removeClass('current');
            } else {
                that.parentsUntil('ui-menu-li').addClass('current');
                that.parentsUntil('ui-menu-li').siblings().removeClass('current');
            }             
            return false;
        });

        $menu.find('a').click(function(e){
            e.preventDefault();
            var that = $(this);
            var link = that.attr("href");  
            if(link){
                var menuId = that.attr("menuid");
                if(menuId){
                    if (link.indexOf("?") > 0)
                    	link += "&";
                    else
                    	link += "?";

                    link +="menuId="+menuId;
                }
                var menuCode = that.attr("menucode");
                if(menuCode){
                	if (link.indexOf("?") > 0)
                		link += "&";
                	else
                		link += "?";
                	
                	link +="menuCode="+menuCode;
                }
            	$("#platform_loading").show();
                var encodedUrl = $.base64.encode(link);
                window.location.hash = encodedUrl;
                _hash = window.location.hash;
                $("#_main_frame")[0].src = link; 
                menuCrumbs(menuCode);
            }            
        }) 

        if (option.vertical) {
            $menu.width(option.width);
            $menu.find('li').width(option.width);
            /*$menu.find('a').width($(this).width() - 14);*/
        } else {
            $.each($menuItems.find('li'), function() {
                //$(this).width(option.width);
                $(this).width(option.groupWidth);
                /*if (!($(this).hasClass('ui-menu-li')) && userAgentIndex!==-1) {
                    $(this).children('a').width($(this).width() - 14);
                }*/
            });
        }
        $menu.css('zIndex', option.zIndex);
        $menu.show();
        
        return this;
    }
    $.fn.extend({
        menu: menu
    });
    //module.exports = menu;
})($ || jQuery);