/**
 * @插件名 js-rolling
 * @作用 无缝滚动
 * @作者 ziven
 * @日期 2016-03-14
 * @fix 2016-09-06 修改重叠问题
 * @Version 1.0
 */
;
(function ($, window, document, undefined) {
    var pluginName = "rolling27";
    var defaults = {
        item: 'li',
        length: false,
        speed: 2000
    };
    function Obj(element, options) {
        this._defaults = defaults;
        this.el = element;
        $.extend(this, defaults, options, true);
        this.init();
    };
    Obj.prototype.init = function () {
        var _it = this;
        var $it = _it.$it = $(_it.el)
        _it.run();
        $it.hover(function () {
            _it.stop();
        }, function () {
            _it.run();
        });
    };
    Obj.prototype.roll = function () {
        var _it = this;
        var $items = _it.$it.find(_it.item);
        //不够一屏滚动
        if (!($items.length > _it.length)) {
            return false;
        }
        //滚动
        var $move = $items.first();
        $move.stop(true, true)
             .animate({
                marginTop: $move.outerHeight() * -1
             },
             500,
             function () {
                $items.last().after($move.remove().removeAttr('style'));
             });
    };
    Obj.prototype.stop = function () {
        var _it = this;
        clearTimeout(_it.timer);
    };
    Obj.prototype.run = function () {
        var _it = this;
        _it.timer = setTimeout(function () {
            _it.roll();
            _it.timer = setTimeout(arguments.callee, _it.speed);
        });
    };
    $.fn[pluginName] = function (options) {
        this.each(function () {
            if (!$.data(this, "$" + pluginName)) {
                $.data(this, "$" + pluginName, new Obj(this, options));
            }
        });
        return this;
    };
})(jQuery, window, document);
