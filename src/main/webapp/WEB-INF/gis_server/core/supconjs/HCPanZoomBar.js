/* Copyright (c) 2006-2011 by OpenLayers Contributors (see authors.txt for 
 * full list of contributors). Published under the Clear BSD license.  
 * See http://svn.openlayers.org/trunk/openlayers/license.txt for the
 * full text of the license. */


/**
 * @requires OpenLayers/Control/PanZoom.js
 */

/**
 * Class: OpenLayers.Control.PanZoomBar
 * The PanZoomBar is a visible control composed of a
 * <OpenLayers.Control.PanPanel> and a <OpenLayers.Control.ZoomBar>. 
 * By default it is displayed in the upper left corner of the map as 4
 * directional arrows above a vertical slider.
 *
 * Inherits from:
 *  - <OpenLayers.Control.PanZoom>
 */
OpenLayers.Control.HCPanZoomBar = OpenLayers.Class(OpenLayers.Control.PanZoom, {

    /** 
     * APIProperty: zoomStopWidth
     */
    //zoomStopWidth: 18,
	zoomStopWidth: 12,

    /** 
     * APIProperty: zoomStopHeight
     */
    //zoomStopHeight: 11,
	zoomStopHeight: 6,

    /** 
     * Property: slider
     */
    slider: null,

    /** 
     * Property: sliderEvents
     * {<OpenLayers.Events>}
     */
    sliderEvents: null,
	
	/**
	*
	*
	*/
	btnEvents:null,

    /** 
     * Property: zoombarDiv
     * {DOMElement}
     */
    zoombarDiv: null,
	
	/**
	*
	*
	*/
	directDiv: null,
	
	/**
	*
	*
	*/
	directImg:null,
	
	/**
	*
	*
	*/
	imgLocation:null,
	
	/**
	*
	*
	*/
	blueZoomDiv:null,
	

    /** 
     * Property: divEvents
     * {<OpenLayers.Events>}
     */
    divEvents: null,

    /** 
     * APIProperty: zoomWorldIcon
     * {Boolean}
     */
    zoomWorldIcon: false,

    /**
     * APIProperty: panIcons
     * {Boolean} Set this property to false not to display the pan icons. If
     * false the zoom world icon is placed under the zoom bar. Defaults to
     * true.
     */
    panIcons: true,

    /**
     * APIProperty: forceFixedZoomLevel
     * {Boolean} Force a fixed zoom level even though the map has 
     *     fractionalZoom
     */
    forceFixedZoomLevel: false,

    /**
     * Property: mouseDragStart
     * {<OpenLayers.Pixel>}
     */
    mouseDragStart: null,

    /**
     * Property: deltaY
     * {Number} The cumulative vertical pixel offset during a zoom bar drag.
     */
    deltaY: null,

    /**
     * Property: zoomStart
     * {<OpenLayers.Pixel>}
     */
    zoomStart: null,

    /**
     * Constructor: OpenLayers.Control.PanZoomBar
     */ 

    /**
     * APIMethod: destroy
     */
    destroy: function() {
		this._removeDirectDiv();
        this._removeZoomBar();

        this.map.events.un({
            "changebaselayer": this.redraw,
            scope: this
        });

        OpenLayers.Control.PanZoom.prototype.destroy.apply(this, arguments);

		this.imgLocation=null;
		this.blueZoomDiv=null;
        delete this.mouseDragStart;
        delete this.zoomStart;
    },
    
    /**
     * Method: setMap
     * 
     * Parameters:
     * map - {<OpenLayers.Map>} 
     */
    setMap: function(map) {
        OpenLayers.Control.PanZoom.prototype.setMap.apply(this, arguments);
        this.map.events.register("changebaselayer", this, this.redraw);
    },

    /** 
     * Method: redraw
     * clear the div and start over.
     */
    redraw: function() {
        if (this.div != null) {
			this._removeDirectDiv();
            this.removeButtons();
            this._removeZoomBar();
        }  
        this.draw();
    },
    
    /**
    * Method: draw 
    *
    * Parameters:
    * px - {<OpenLayers.Pixel>} 
    */
    draw: function(px) {
        // initialize our internal div
        OpenLayers.Control.prototype.draw.apply(this, arguments);
        px = this.position.clone();
		
		imgLocation = OpenLayers.Util.getImagesLocation() + "directbg.png";
        // place the controls
        this.buttons = [];

        //var sz = new OpenLayers.Size(18,18);
		var sz = new OpenLayers.Size(44,44);
        if (this.panIcons) {
            var centered = new OpenLayers.Pixel(px.x+sz.w/2, px.y);
            var wposition = sz.w;
			
			//创建以背景div
			var divId = this.id + '_directdiv';
            this.directDiv = OpenLayers.Util.createDiv(divId, 
                px, sz, null, "absolute", null, "hidden", null
            );
			
			this.directImg = OpenLayers.Util.createAlphaImageDiv(
                                    this.id + "_directbg", 
                                    null, new OpenLayers.Size(44,464), imgLocation, "absolute");
			this._creatimg(0,0);
			this.directDiv.appendChild(this.directImg)
			this.div.appendChild(this.directDiv);

			
            if (this.zoomWorldIcon) {
                centered = new OpenLayers.Pixel(px.x+sz.w, px.y);
            }

            this._addPanButton("panup", centered.add(-7, 0), new OpenLayers.Size(15,15),new OpenLayers.Pixel(0, -335));  //上移按钮
            px.y = centered.y+sz.h/2-8;
            this._addPanButton("panleft", px, new OpenLayers.Size(15,15),new OpenLayers.Pixel(0, -335));  //左移按钮
            if (this.zoomWorldIcon) {
                this._addButton("zoomworld", px.add(sz.w, 0), sz,new OpenLayers.Pixel(0, -225));

                wposition *= 2;
            }
			this._addPanButton("panright", px.add(30, 0), new OpenLayers.Size(15,15),new OpenLayers.Pixel(0, -335));  //右移按钮
            this._addPanButton("pandown", centered.add(-7, 29), new OpenLayers.Size(15,15),new OpenLayers.Pixel(0, -335));  //下移按钮
            this._addButton("zoomin", centered.add(-10, sz.h+5), new OpenLayers.Size(20,20),new OpenLayers.Pixel(0, -220));  //放大
            centered = this._addZoomBar(centered.add(-9, sz.h + 23));  //缩放条
            this._addButton("zoomout", centered.add(0,0), new OpenLayers.Size(20,20),new OpenLayers.Pixel(-1, -264));  //缩小
        }
        else {
            this._addButton("zoomin", px.add(0, 0), new OpenLayers.Size(20,20),new OpenLayers.Pixel(0, -220));  //放大
            centered = this._addZoomBar(px.add(1, sz.h-26)); //缩放条
            this._addButton("zoomout", centered.add(0,0), new OpenLayers.Size(20,20),new OpenLayers.Pixel(0, -264)); //缩小
            if (this.zoomWorldIcon) {
                centered = centered.add(0, sz.h+3);
                this._addButton("zoomworld", centered, sz,new OpenLayers.Pixel(0, -225));
            }
        }
        return this.div;
    },
	
	_creatbtn:function(divId,xy,sz,position){
		var directDiv = OpenLayers.Util.createDiv(divId, 
			xy, sz, null, "absolute", null, "hidden", null
		);
			
		var directImg = OpenLayers.Util.createAlphaImageDiv(
								divId, 
								null, new OpenLayers.Size(45,464), imgLocation, "absolute");
		directImg.style.left=position.x+'px';
		directImg.style.top =position.y+'px';
		//directImg.style.background="#000000";
		directDiv.appendChild(directImg)
		return directDiv;
	},
	//改变移动圆盘地图
	_creatimg:function(left,top){
		this.directImg.style.left=left+'px';
		this.directImg.style.top =top+'px';
	},
	
	/**
     * Method: _addButton
     * 
     * Parameters:
     * id - {String} 
     * img - {String} 
     * xy - {<OpenLayers.Pixel>} 
     * sz - {<OpenLayers.Size>} 
     * 
     * Returns:
     * {DOMElement} A Div (an alphaImageDiv, to be precise) that contains the
     *     image of the button, and has all the proper event handlers set.
     */
    _addButton:function(id,xy, sz,position) {
        /*var imgLocation = OpenLayers.Util.getImagesLocation() + img;
        var btn = OpenLayers.Util.createAlphaImageDiv(
                                    this.id + "_" + id, 
                                    xy, sz, imgLocation, "absolute");*/
		var btn=this._creatbtn(this.id + "_" + id,xy,sz,position)
                btn.style.cursor = "pointer";
        //we want to add the outer div
        this.div.appendChild(btn);
        btn.action = id;
        btn.className = "olButton";
    
        //we want to remember/reference the outer div
        this.buttons.push(btn);
        return btn;
    },
	
	/**
     * Method: _addButton
     * 
     * Parameters:
     * id - {String} 
     * img - {String} 
     * xy - {<OpenLayers.Pixel>} 
     * sz - {<OpenLayers.Size>} 
     * 
     * Returns:
     * {DOMElement} A Div (an alphaImageDiv, to be precise) that contains the
     *     image of the button, and has all the proper event handlers set.
     */
    _addPanButton:function(id,xy, sz,position) {
		var btn=this._creatbtn(this.id + "_" + id,xy,sz,position)
        btn.style.cursor = "pointer";
        //we want to add the outer div
        this.div.appendChild(btn);
        btn.action = id;
        btn.className = "olButton";
		
		this.btnEvents=new OpenLayers.Events(this, btn, null, true,
                                            {includeXY: true});
		this.btnEvents.on({
			"mouseover":this.buttonMouseOver,  //鼠标鱼骨盘样式
			"mouseout":this.buttonMouseOut
		});

        this.buttons.push(btn);
        return btn;
    },


    /** 
    * Method: _addZoomBar
    * 
    * Parameters:
    * location - {<OpenLayers.Pixel>} where zoombar drawing is to start.
    */
    _addZoomBar:function(centered) {
        var imgLocation = OpenLayers.Util.getImagesLocation();
        
        var id = this.id + "_" + this.map.id;
        var zoomsToEnd = this.map.getNumZoomLevels() - 1 - this.map.getZoom();
        /*var slider = OpenLayers.Util.createAlphaImageDiv(id,
                       centered.add(-1, zoomsToEnd * this.zoomStopHeight), 
                       new OpenLayers.Size(20,9), 
                       imgLocation+"slider.png",
                       "absolute");*/
		var slider =this._creatbtn(id,centered.add(0, zoomsToEnd * this.zoomStopHeight), new OpenLayers.Size(20,10),new OpenLayers.Pixel(0, -309))
        slider.style.cursor = "move";
		//slider.style.cursor= "url(\'"+imgLocation+"openhand.cur\'),auto";
        this.slider = slider;
        //改变鼠标形状
		//this.slider.onmousedown=function(){this.style.cursor="url(\'"+imgLocation+"closedhand.cur\'),auto"}
		//this.slider.onmouseup=function(){this.style.cursor="url(\'"+imgLocation+"openhand.cur\'),auto"}
		
        this.sliderEvents = new OpenLayers.Events(this, slider, null, true,
                                            {includeXY: true});
        this.sliderEvents.on({
            "touchstart": this.zoomBarDown,
            "touchmove": this.zoomBarDrag,
            "touchend": this.zoomBarUp,
            "mousedown": this.zoomBarDown,
            "mousemove": this.zoomBarDrag,
            "mouseup": this.zoomBarUp,
            "dblclick": this.doubleClick,
            "click": this.doubleClick
        });
        
        var sz = new OpenLayers.Size();
        sz.h = this.zoomStopHeight * this.map.getNumZoomLevels();
        sz.w = this.zoomStopWidth;
        var div = null;
        
        if (OpenLayers.Util.alphaHack()) {
            var id = this.id + "_" + this.map.id;
            div = OpenLayers.Util.createAlphaImageDiv(id, centered,
                                      new OpenLayers.Size(sz.w, 
                                              this.zoomStopHeight),
                                      imgLocation + "zoombar.png", 
                                      "absolute", null, "crop");
            div.style.height = sz.h + "px";
        } else {
            /*div = OpenLayers.Util.createDiv(
                        'OpenLayers_Control_PanZoomBar_Zoombar' + this.map.id,
                        centered,
                        sz,
                        imgLocation+"zoombar.png");*/
			div =this._creatZoombg('OpenLayers_Control_PanZoomBar_Zoombar' + this.map.id,centered.add(3,0), sz,new OpenLayers.Pixel(-21, -220))
        }
        div.style.cursor = "pointer";
        this.zoombarDiv = div;
        
        this.divEvents = new OpenLayers.Events(this, div, null, true, 
                                                {includeXY: true});
        this.divEvents.on({
            "touchmove": this.passEventToSlider,
            "mousedown": this.divClick,
            "mousemove": this.passEventToSlider,
            "dblclick": this.doubleClick,
            "click": this.doubleClick
        });
        
        this.div.appendChild(div);

        this.startTop = parseInt(div.style.top);
        this.div.appendChild(slider);

        this.map.events.register("zoomend", this, this.moveZoomBar);

        centered = centered.add(0, 
            this.zoomStopHeight * this.map.getNumZoomLevels());
		this._changZoomBlueImg(this.startTop-1);
        return centered; 
    },
	
	//创建鱼骨条背景图片
	_creatZoombg:function(divId,xy,sz,position){
		var zoomDiv = OpenLayers.Util.createDiv(divId, 
			xy, sz, null, "absolute", null, "hidden", null
		);
		var zoomImg=OpenLayers.Util.createAlphaImageDiv(
								divId, 
								null, new OpenLayers.Size(45,464), imgLocation, "absolute");
		zoomImg.style.left=(position.x-10)+'px';
		zoomImg.style.top =position.y+'px';
		
		zoomDiv.appendChild(zoomImg)
		
		this.blueZoomDiv=OpenLayers.Util.createDiv(divId, 
			null, sz, null, "absolute", null, "hidden", null
		);
		
		var blueZoomImg = OpenLayers.Util.createAlphaImageDiv(
								divId+"_blue", 
								null, new OpenLayers.Size(45,464), imgLocation, "absolute");
		blueZoomImg.style.left=position.x+'px';
		blueZoomImg.style.top =position.y+'px';
		//this.blueZoomDiv.style.background="#000000";
		this.blueZoomDiv.appendChild(blueZoomImg)
		zoomDiv.appendChild(this.blueZoomDiv)
		return zoomDiv;
	},
	
	//改变鱼骨条蓝色图片
	_changZoomBlueImg:function(top){
		this.blueZoomDiv.style.height =top+'px';
	},
    
	/**
	*
	*/
	_removeDirectDiv: function(){
		if(this.btnEvents!=null){
			this.btnEvents.un({
				"mouseover":this.buttonMouseOver,  //鼠标鱼骨盘样式
				"mouseout":this.buttonMouseOut
			});
			this.btnEvents.destroy();
			this.btnEvents=null;
		}
		if(this.directDiv!=null){
			this.directDiv.removeChild(this.directImg);
			this.directImg=null;
			this.div.removeChild(this.directDiv);
			this.directDiv=null;
		}
	},
    /**
     * Method: _removeZoomBar
     */
    _removeZoomBar: function() {
        this.sliderEvents.un({
            "touchmove": this.zoomBarDrag,
            "mousedown": this.zoomBarDown,
            "mousemove": this.zoomBarDrag,
            "mouseup": this.zoomBarUp,
            "dblclick": this.doubleClick,
            "click": this.doubleClick
        });
        this.sliderEvents.destroy();

        this.divEvents.un({
            "touchmove": this.passEventToSlider,
            "mousedown": this.divClick,
            "mousemove": this.passEventToSlider,
            "dblclick": this.doubleClick,
            "click": this.doubleClick
        });
        this.divEvents.destroy();
        
        this.div.removeChild(this.zoombarDiv);
        this.zoombarDiv = null;
        this.div.removeChild(this.slider);
        this.slider = null;
        
        this.map.events.unregister("zoomend", this, this.moveZoomBar);
    },
    
    /**
     * Method: passEventToSlider
     * This function is used to pass events that happen on the div, or the map,
     * through to the slider, which then does its moving thing.
     *
     * Parameters:
     * evt - {<OpenLayers.Event>} 
     */
    passEventToSlider:function(evt) {
        this.sliderEvents.handleBrowserEvent(evt);
    },
    
    /**
     * Method: divClick
     * Picks up on clicks directly on the zoombar div
     *           and sets the zoom level appropriately.
     */
    divClick: function (evt) {
        if (!OpenLayers.Event.isLeftClick(evt)) {
            return;
        }
        var levels = evt.xy.y / this.zoomStopHeight;
        if(this.forceFixedZoomLevel || !this.map.fractionalZoom) {
            levels = Math.floor(levels);
        }    
        var zoom = (this.map.getNumZoomLevels() - 1) - levels; 
        zoom = Math.min(Math.max(zoom, 0), this.map.getNumZoomLevels() - 1);
		this._changZoomBlueImg(evt.xy.y);
        this.map.zoomTo(zoom);
        OpenLayers.Event.stop(evt);
    },
    
    /*
     * Method: zoomBarDown
     * event listener for clicks on the slider
     *
     * Parameters:
     * evt - {<OpenLayers.Event>} 
     */
    zoomBarDown:function(evt) {
        if (!OpenLayers.Event.isLeftClick(evt) && !OpenLayers.Event.isSingleTouch(evt)) {
            return;
        }
		//this.slider.style.cursor= "url(\'"+imgLocation+"closedhand.cur\')";
        this.map.events.on({
            "touchmove": this.passEventToSlider,
            "mousemove": this.passEventToSlider,
            "mouseup": this.passEventToSlider,
            scope: this
        });
        this.mouseDragStart = evt.xy.clone();
        this.zoomStart = evt.xy.clone();
        this.div.style.cursor = "move";
        // reset the div offsets just in case the div moved
        this.zoombarDiv.offsets = null; 
        OpenLayers.Event.stop(evt);
    },
    
    /*
     * Method: zoomBarDrag
     * This is what happens when a click has occurred, and the client is
     * dragging.  Here we must ensure that the slider doesn't go beyond the
     * bottom/top of the zoombar div, as well as moving the slider to its new
     * visual location
     *
     * Parameters:
     * evt - {<OpenLayers.Event>} 
     */
    zoomBarDrag:function(evt) {
	//console.log("UserY","tttttttttttttttttttt")
        if (this.mouseDragStart != null) {
            var deltaY = this.mouseDragStart.y - evt.xy.y;
            var offsets = OpenLayers.Util.pagePosition(this.zoombarDiv);
            if ((evt.clientY - offsets[1]) > 0 && 
                (evt.clientY - offsets[1]) < parseInt(this.zoombarDiv.style.height) - 2) {
				//console.log("UserY", evt.clientY +"%%%%%%%%%%%%%%%"+ offsets[1])
                var newTop = parseInt(this.slider.style.top) - deltaY;
				//蓝色块随鼠标动
				this._changZoomBlueImg(evt.clientY - offsets[1]);
                this.slider.style.top = newTop+"px";
                this.mouseDragStart = evt.xy.clone();
            }
            // set cumulative displacement
            this.deltaY = this.zoomStart.y - evt.xy.y;
            OpenLayers.Event.stop(evt);
        }
    },
	
	buttonMouseOver:function(evt){
		var action="";
		if(navigator.userAgent.indexOf("Firefox")>0){
			action=evt.currentTarget.action;
			//console.log("UserY", action)
		}else{
			action=evt.srcElement.id.split("_")[2];
		}
		switch (action) {
            case "panup": 
                this._creatimg(0,-44);
                break;
            case "pandown": 
                this._creatimg(0,-132);
                break;
            case "panleft": 
                this._creatimg(0,-176);
                break;
            case "panright": 
                this._creatimg(0,-88);
                break;
			default:
			   break;
		}		
	},
	
	buttonMouseOut:function(evt){
		this._creatimg(0,0);
	},
    
    /*
     * Method: zoomBarUp
     * Perform cleanup when a mouseup event is received -- discover new zoom
     * level and switch to it.
     *
     * Parameters:
     * evt - {<OpenLayers.Event>} 
     */
    zoomBarUp:function(evt) {
		if (!OpenLayers.Event.isLeftClick(evt) && evt.type !== "touchend") {
            return;
        }
        if (this.mouseDragStart) {
            this.div.style.cursor="";
            this.map.events.un({
                "touchmove": this.passEventToSlider,
                "mouseup": this.passEventToSlider,
                "mousemove": this.passEventToSlider,
                scope: this
            });
            var zoomLevel = this.map.zoom;
            if (!this.forceFixedZoomLevel && this.map.fractionalZoom) {
                zoomLevel += this.deltaY/this.zoomStopHeight;
                zoomLevel = Math.min(Math.max(zoomLevel, 0), 
                                     this.map.getNumZoomLevels() - 1);
            } else {
                zoomLevel += this.deltaY/this.zoomStopHeight;
                zoomLevel = Math.max(Math.round(zoomLevel), 0); 			
            }
			//console.log("UserY",this.map.getNumZoomLevels());
            this.map.zoomTo(zoomLevel);
            this.mouseDragStart = null;
            this.zoomStart = null;
            this.deltaY = 0;
            OpenLayers.Event.stop(evt);
        }
    },
    
    /*
    * Method: moveZoomBar
    * Change the location of the slider to match the current zoom level.
    */
    moveZoomBar:function() {
        var newTop = 
            ((this.map.getNumZoomLevels()-1) - this.map.getZoom()) * 
            this.zoomStopHeight + this.startTop + 1;
        this.slider.style.top = newTop + "px";
		this._changZoomBlueImg(((this.map.getNumZoomLevels()-1) - this.map.getZoom()) * this.zoomStopHeight+10);
    }, 	
    
    CLASS_NAME: "OpenLayers.Control.HCPanZoomBar"
});
