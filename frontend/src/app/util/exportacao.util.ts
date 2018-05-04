export class ExportacaoUtil {
    public static download(downloadUrl, filename) {
        const a: any = document.createElement('a');
        a.style = 'display: none';
        a.href = downloadUrl;
        a.download = filename;
        document.body.appendChild(a);
        a.click();
    }

    public static imprimir(downloadUrl) {
        const frame: any = document.createElement('iframe');
        frame.style = 'display: none';
        frame.src = downloadUrl;
        document.body.appendChild(frame);
        frame.onload = function () {
            frame.contentWindow.focus();
            frame.contentWindow.print();
        };
    }
}