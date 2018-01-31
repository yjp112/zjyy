/* Copyright (c) 2006-2011 by OpenLayers Contributors (see authors.txt for 
 * full list of contributors). Published under the Clear BSD license.  
 * See http://svn.openlayers.org/trunk/openlayers/license.txt for the
 * full text of the license. */

/**
 * @requires OpenLayers/Popup/Framed.js
 * @requires OpenLayers/Util.js
 * @requires OpenLayers/BaseTypes/Bounds.js
 * @requires OpenLayers/BaseTypes/Pixel.js
 * @requires OpenLayers/BaseTypes/Size.js
 */

/**
 * Class: OpenLayers.Popup.FramedCloud
 * 
 * Inherits from: 
 *  - <OpenLayers.Popup.Framed>
 */
OpenLayers.Popup.GisFramedCloud = 
  OpenLayers.Class(OpenLayers.Popup.Framed, {

    /** 
     * Property: contentDisplayClass
     * {String} The CSS class of the popup content div.
     */
    contentDisplayClass: "olFramedCloudPopupContent1",

    /**
     * APIProperty: autoSize
     * {Boolean} Framed Cloud is autosizing by default.
     */
    autoSize: true,
	titleHTML:null,

    /**
     * APIProperty: panMapIfOutOfView
     * {Boolean} Framed Cloud does pan into view by default.
     */
    panMapIfOutOfView: true,

    /**
     * APIProperty: imageSize
     * {<OpenLayers.Size>}
     */
    imageSize: new OpenLayers.Size(1276, 736),

    /**
     * APIProperty: isAlphaImage
     * {Boolean} The FramedCloud does not use an alpha image (in honor of the 
     *     good ie6 folk out there)
     */
    isAlphaImage: false,

    /** 
     * APIProperty: fixedRelativePosition
     * {Boolean} The Framed Cloud popup works in just one fixed position.
     */
    fixedRelativePosition: false,

    /**
     * Property: positionBlocks
     * {Object} Hash of differen position blocks, keyed by relativePosition
     *     two-character code string (ie "tl", "tr", "bl", "br")
     */
    positionBlocks: {
        "tl": {
            'offset': new OpenLayers.Pixel(10, -20),
            'padding': new OpenLayers.Bounds(8, 40, 8, 12),
            'blocks': [
                { // top-left
                    size: new OpenLayers.Size('auto', 'auto'),
                    anchor: new OpenLayers.Bounds(0, 52, 22, 0),//(left,bottom,right,top) 
                    position: new OpenLayers.Pixel(0, 0)
                },
                { //top-right
                    size: new OpenLayers.Size(22, 'auto'),
                    anchor: new OpenLayers.Bounds(null, 52, 0, 0),//(left,bottom,right,top)
                    position: new OpenLayers.Pixel(-1260, 0)
                },
                { //bottom-left
                    size: new OpenLayers.Size('auto', 21),
                    anchor: new OpenLayers.Bounds(0, 31, 22, null),//(left,bottom,right,top)
                    position: new OpenLayers.Pixel(0, -637)
                },
                { //bottom-right
                    size: new OpenLayers.Size(22, 21),
                    anchor: new OpenLayers.Bounds(null, 31, 0, null),//(left,bottom,right,top)
                    position: new OpenLayers.Pixel(-1260, -637)
                },
                { // stem
                    size: new OpenLayers.Size(81, 35),
                    anchor: new OpenLayers.Bounds(null, 0, 0, null),//(left,bottom,right,top)
                    position: new OpenLayers.Pixel(15, -676)
                }
            ]
        },
        "tr": {
            'offset': new OpenLayers.Pixel(-3, -20),  //气泡的偏移
            'padding': new OpenLayers.Bounds(8, 40, 8, 12),  //气泡内部padding
            'blocks': [
                { // top-left
                    size: new OpenLayers.Size('auto', 'auto'),
                    anchor: new OpenLayers.Bounds(0, 51, 22, 0),
                    position: new OpenLayers.Pixel(0, 0)
                },
                { //top-right
                    size: new OpenLayers.Size(22, 'auto'),
                    anchor: new OpenLayers.Bounds(null, 50, 0, 0),
                    position: new OpenLayers.Pixel(-1260, 0)
                },
                { //bottom-left
                    size: new OpenLayers.Size('auto', 19),
                    anchor: new OpenLayers.Bounds(0, 32, 22, null),
                    position: new OpenLayers.Pixel(0, -638)
                },
                { //bottom-right
                    size: new OpenLayers.Size(22, 19),
                    anchor: new OpenLayers.Bounds(null, 32, 0, null),
                    position: new OpenLayers.Pixel(-1260, -638)
                },
                { // stem
                    size: new OpenLayers.Size(81, 35),
                    anchor: new OpenLayers.Bounds(0, 0, null, null),
                    position: new OpenLayers.Pixel(-240, -680)
                }
            ]
        },
        "bl": {
            'offset': new OpenLayers.Pixel(0, 0),
            'padding': new OpenLayers.Bounds(8, 9, 8, 44),
            'blocks': [
                { // top-left
                    size: new OpenLayers.Size('auto', 'auto'),
                    anchor: new OpenLayers.Bounds(0, 21, 22, 32),
                    position: new OpenLayers.Pixel(0, 0)
                },
                { //top-right
                    size: new OpenLayers.Size(22, 'auto'),
                    anchor: new OpenLayers.Bounds(null, 21, 0, 32),
                    position: new OpenLayers.Pixel(-1260, 0)
                },
                { //bottom-left
                    size: new OpenLayers.Size('auto', 21),
                    anchor: new OpenLayers.Bounds(0, 0, 22, null),
                    position: new OpenLayers.Pixel(0, -637)
                },
                { //bottom-right
                    size: new OpenLayers.Size(22, 21),
                    anchor: new OpenLayers.Bounds(null, 0, 0, null),
                    position: new OpenLayers.Pixel(-1260, -637)
                },
                { // stem 气泡指针尖
                    size: new OpenLayers.Size(81, 33),  //块大小
                    anchor: new OpenLayers.Bounds(null, null, 0, 0),  //相对于父div偏移(left,bottom,right,top)
                    position: new OpenLayers.Pixel(-80, -699)  //在气泡大图上的截取位置
                }
            ]
        },
        "br": {
            'offset': new OpenLayers.Pixel(-10, 0),
            'padding': new OpenLayers.Bounds(8, 9, 8, 44),
            'blocks': [
                { // top-left
                    size: new OpenLayers.Size('auto', 'auto'),
                    anchor: new OpenLayers.Bounds(0, 21, 22, 32),
                    position: new OpenLayers.Pixel(0, 0)
                },
                { //top-right
                    size: new OpenLayers.Size(22, 'auto'),
                    anchor: new OpenLayers.Bounds(null, 21, 0, 32),
                    position: new OpenLayers.Pixel(-1260, 0)
                },
                { //bottom-left
                    size: new OpenLayers.Size('auto', 21),
                    anchor: new OpenLayers.Bounds(0, 0, 22, null),
                    position: new OpenLayers.Pixel(0, -637)
                },
                { //bottom-right
                    size: new OpenLayers.Size(22, 21),
                    anchor: new OpenLayers.Bounds(null, 0, 0, null),
                    position: new OpenLayers.Pixel(-1260, -637)
                },
                { // stem
                    size: new OpenLayers.Size(81, 33),
                    anchor: new OpenLayers.Bounds(0, null, null, 0),
                    position: new OpenLayers.Pixel(-330, -699)
                }
            ]
        }
    },

    /**
     * APIProperty: minSize
     * {<OpenLayers.Size>}
     */
    minSize: new OpenLayers.Size(105, 10),

    /**
     * APIProperty: maxSize
     * {<OpenLayers.Size>}
     */
    maxSize: new OpenLayers.Size(1200, 660),
	
	titleDisplayClass:"titleDisplayClass",

    /** 
     * Constructor: OpenLayers.Popup.FramedCloud
     * 
     * Parameters:
     * id - {String}
     * lonlat - {<OpenLayers.LonLat>}
     * contentSize - {<OpenLayers.Size>}
	 * titleHTML - {String}
     * contentHTML - {String}
     * anchor - {Object} Object to which we'll anchor the popup. Must expose 
     *     a 'size' (<OpenLayers.Size>) and 'offset' (<OpenLayers.Pixel>) 
     *     (Note that this is generally an <OpenLayers.Icon>).
     * closeBox - {Boolean}
     * closeBoxCallback - {Function} Function to be called on closeBox click.
     */
    initialize:function(id, lonlat, contentSize,titleHTML, contentHTML, anchor, closeBox, 
                        closeBoxCallback) {					
		var newArguments=[id, lonlat, contentSize, contentHTML, anchor, closeBox, 
                        closeBoxCallback]
        this.imageSrc = OpenLayers.Util.getImagesLocation() + 'cloud-popup-relative1.png';
        OpenLayers.Popup.Framed.prototype.initialize.apply(this, newArguments);
        //this.contentDiv.className = this.contentDisplayClass;
		this.titleHTML = (titleHTML != null) ? titleHTML : "";
		this.contentHTML=this.titleContent();
    },
	
	calculateRelativePosition:function (px) {
		return 'tr';
	},
	
	/**
	*
	*/	
	titleContent:function(){
		var newcontentHTML = "<div class='" + this.titleDisplayClass+ "'>" + 
            this.titleHTML + 
            "</div><div style='overflow:hidden;'>"+this.contentHTML+"</div>";			
		return newcontentHTML;
	},
	
	/**
	*更新标题
	*/
	setTitle:function(titleHTML){
		this.titleHTML=titleHTML;
		this.contentHTML=this.titleContent();
		this.setContentHTML(this.contentHTML);
	},

    /** 
     * APIMethod: destroy
     */
    destroy: function() {
		this.titleHTML=null;
        OpenLayers.Popup.Framed.prototype.destroy.apply(this, arguments);
    },
	/** 
    * Method: calculateNewPx
    * Besides the standard offset as determined by the Anchored class, our 
    *     Framed popups have a special 'offset' property for each of their 
    *     positions, which is used to offset the popup relative to its anchor.
    * 
    * Parameters:
    * px - {<OpenLayers.Pixel>}
    * 
    * Returns:
    * {<OpenLayers.Pixel>} The the new px position of the popup on the screen
    *     relative to the passed-in px.
    */
    calculateNewPx:function(px) {
        var newPx = OpenLayers.Popup.Anchored.prototype.calculateNewPx.apply(
            this, arguments
        );

        newPx = newPx.offset(this.positionBlocks[this.relativePosition].offset);

		var popupoffset=Math.floor((this.div.style.width.replace("px",""))/2);
		if(this.relativePosition=="tl"||this.relativePosition=="bl"){
			newPx=newPx.offset(new OpenLayers.Pixel((popupoffset-41),0))
		}
		if(this.relativePosition=="tr"||this.relativePosition=="br"){
			newPx=newPx.offset(new OpenLayers.Pixel(-(popupoffset-41),0))
		}
		
        return newPx;
    },
	
	/**
    * Method: updateBlocks
    * Internal method, called on initialize and when the popup's relative
    *     position has changed. This function takes care of re-positioning
    *     the popup's blocks in their appropropriate places.
    */
    updateBlocks: function() {
        if (!this.blocks) {
            this.createBlocks();
        }
        
        if (this.size && this.relativePosition) {
		var popupoffset=Math.floor((this.div.style.width.replace("px",""))/2);
		
            var position = this.positionBlocks[this.relativePosition];
            for (var i = 0; i < position.blocks.length; i++) {
    
                var positionBlock = position.blocks[i];
                var block = this.blocks[i];
    
                // adjust sizes
                var l = positionBlock.anchor.left;
                var b = positionBlock.anchor.bottom;
                var r = positionBlock.anchor.right;
                var t = positionBlock.anchor.top;
				if(i==4){
					if(this.relativePosition=="tl"||this.relativePosition=="bl"){
						r=r+popupoffset-40;
					}
					if(this.relativePosition=="tr"||this.relativePosition=="br"){
						l=l+popupoffset-40;
					}
				}
                //note that we use the isNaN() test here because if the 
                // size object is initialized with a "auto" parameter, the 
                // size constructor calls parseFloat() on the string, 
                // which will turn it into NaN
                //
                var w = (isNaN(positionBlock.size.w)) ? this.size.w - (r + l) 
                                                      : positionBlock.size.w;
    
                var h = (isNaN(positionBlock.size.h)) ? this.size.h - (b + t) 
                                                      : positionBlock.size.h;
    
                block.div.style.width = (w < 0 ? 0 : w) + 'px';
                block.div.style.height = (h < 0 ? 0 : h) + 'px';
    
                block.div.style.left = (l != null) ? l + 'px' : '';
                block.div.style.bottom = (b != null) ? b + 'px' : '';
                block.div.style.right = (r != null) ? r + 'px' : '';            
                block.div.style.top = (t != null) ? t + 'px' : '';
    
                block.image.style.left = positionBlock.position.x + 'px';
                block.image.style.top = positionBlock.position.y + 'px';
            }
    
            this.contentDiv.style.left = this.padding.left + "px";
            this.contentDiv.style.top = this.padding.top + "px";
        }
    },

    CLASS_NAME: "OpenLayers.Popup.GisFramedCloud"
});
