/**
 * 统一弹框
 * type 弹框类型
 * title 标题
 * callback
 */
(function( window ){
    var

        // Use the correct document accordingly with window argument (sandbox)
        location = window.location,
        document = window.document,
        docElem = document.documentElement,

        // [[Class]] -> type pairs
        class2type = {},

        // List of deleted data cache ids, so we can reuse them
        core_deletedIds = [],

        // Map over jQuery in case of overwrite
        layer = window.layer,

        core_version = "1.0.0",

        // Save a reference to some core methods
        core_concat = core_deletedIds.concat,
        core_push = core_deletedIds.push,
        core_slice = core_deletedIds.slice,
        core_indexOf = core_deletedIds.indexOf,
        core_toString = class2type.toString,
        core_hasOwn = class2type.hasOwnProperty,
        core_trim = core_version.trim,

        layer = function (element){
            return new layer.fn.init(element)
        };

    layer.fn = layer.prototype = {
        init: function (element) {
            if(!element)return this;

            // if(element instanceof Object){
            //     this.type = element.type || 1,
            //     this.title = element.title || '图片素材库',
            //     this.content = element.content,
            //     this.success = element.success,
            //     this.cancel = element.cancel
            // }

            return  core_push.call( this, element );

        },

        aa: function(){

        },

        length: 0
    };

    layer.fn.init.prototype = layer.fn;
    layer.extend = layer.fn.extend = function() {
        // var options, name, src, copy, copyIsArray, clone,
        //     target = arguments[0] || {},
        //     i = 1,
        //     length = arguments.length,
        //     deep = false;
        //
        // // Handle a deep copy situation
        // if ( typeof target === "boolean" ) {
        //     deep = target;
        //     target = arguments[1] || {};
        //     // skip the boolean and the target
        //     i = 2;
        // }
        //
        // // Handle case when target is a string or something (possible in deep copy)
        // if ( typeof target !== "object" && !jQuery.isFunction(target) ) {
        //     target = {};
        // }
        //
        // // extend jQuery itself if only one argument is passed
        // if ( length === i ) {
        //     target = this;
        //     --i;
        // }
        //
        // for ( ; i < length; i++ ) {
        //     // Only deal with non-null/undefined values
        //     if ( (options = arguments[ i ]) != null ) {
        //         // Extend the url object
        //         for ( name in options ) {
        //             src = target[ name ];
        //             copy = options[ name ];
        //
        //             // Prevent never-ending loop
        //             if ( target === copy ) {
        //                 continue;
        //             }
        //
        //             // Recurse if we're merging plain objects or arrays
        //             if ( deep && copy && ( layer.isPlainObject(copy) || (copyIsArray = layer.isArray(copy)) ) ) {
        //                 if ( copyIsArray ) {
        //                     copyIsArray = false;
        //                     clone = src && layer.isArray(src) ? src : [];
        //
        //                 } else {
        //                     clone = src && layer.isPlainObject(src) ? src : {};
        //                 }
        //
        //                 // Never move original objects, clone them
        //                 target[ name ] = layer.extend( deep, clone, copy );
        //
        //                 // Don't bring in undefined values
        //             } else if ( copy !== undefined ) {
        //                 target[ name ] = copy;
        //             }
        //         }
        //     }
        // }

        // Return the modified object
        //return target;
    };

    // layer.extend({
    //
    //     // See test/unit/core.js for details concerning isFunction.
    //     // Since version 1.3, DOM methods and functions like alert
    //     // aren't supported. They return false on IE (#2968).
    //     isFunction: function( obj ) {
    //         return jQuery.type(obj) === "function";
    //     },
    //
    //     isArray: Array.isArray,
    //
    //     isWindow: function( obj ) {
    //         return obj != null && obj === obj.window;
    //     },
    //
    //     isNumeric: function( obj ) {
    //         return !isNaN( parseFloat(obj) ) && isFinite( obj );
    //     },
    //
    //     type: function( obj ) {
    //         if ( obj == null ) {
    //             return String( obj );
    //         }
    //         // Support: Safari <= 5.1 (functionish RegExp)
    //         return typeof obj === "object" || typeof obj === "function" ?
    //             class2type[ core_toString.call(obj) ] || "object" :
    //             typeof obj;
    //     },
    //
    //     isPlainObject: function( obj ) {
    //         // Not plain objects:
    //         // - Any object or value whose internal [[Class]] property is not "[object Object]"
    //         // - DOM nodes
    //         // - window
    //         if ( jQuery.type( obj ) !== "object" || obj.nodeType || jQuery.isWindow( obj ) ) {
    //             return false;
    //         }
    //
    //         // Support: Firefox <20
    //         // The try/catch suppresses exceptions thrown when attempting to access
    //         // the "constructor" property of certain host objects, ie. |window.location|
    //         // https://bugzilla.mozilla.org/show_bug.cgi?id=814622
    //         try {
    //             if ( obj.constructor &&
    //                 !core_hasOwn.call( obj.constructor.prototype, "isPrototypeOf" ) ) {
    //                 return false;
    //             }
    //         } catch ( e ) {
    //             return false;
    //         }
    //
    //         // If the function hasn't returned already, we're confident that
    //         // |obj| is a plain object, created by {} or constructed with new Object
    //         return true;
    //     },
    //
    //     isEmptyObject: function( obj ) {
    //         var name;
    //         for ( name in obj ) {
    //             return false;
    //         }
    //         return true;
    //     }
    // });



    layer.fn.view = function () {

    };

    window.layer = layer
})(window);
