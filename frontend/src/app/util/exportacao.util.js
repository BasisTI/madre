"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var ExportacaoUtil = (function () {
    function ExportacaoUtil() {
    }
    ExportacaoUtil.download = function (downloadUrl, filename) {
        var a = document.createElement('a');
        a.style = 'display: none';
        a.href = downloadUrl;
        a.download = filename;
        document.body.appendChild(a);
        a.click();
    };
    ExportacaoUtil.imprimir = function (downloadUrl) {
        var frame = document.createElement('iframe');
        frame.style = 'display: none';
        frame.src = downloadUrl;
        document.body.appendChild(frame);
        frame.onload = function () {
            frame.contentWindow.focus();
            frame.contentWindow.print();
        };
    };
    return ExportacaoUtil;
}());
exports.ExportacaoUtil = ExportacaoUtil;
