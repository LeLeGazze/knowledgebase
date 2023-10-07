<!DOCTYPE html>
<html lang="en-us">
<head>
    <meta charset="utf-8"/>
    <link rel="stylesheet" href="/static/css/github.min.css"/>
    <link rel="stylesheet" href="/static/css/css.css"/>
    <link rel="stylesheet" type="text/css" href="/static/css/diff2html.min.css"/>
    <script type="text/javascript" src="/static/js/diff2html-ui.min.js"></script>
</head>
<script>
    const diffString = ${msg};


    document.addEventListener('DOMContentLoaded', function () {
        var targetElement = document.getElementById('myDiffElement');
        var configuration = {
            drawFileList: true,
            fileListToggle: true,
            fileListStartVisible: true,
            fileContentToggle: true,
            matching: 'lines',
            outputFormat: 'side-by-side',
            synchronisedScroll: true,
            highlight: true,
            renderNothingWhenEmpty: true,
        };
        var diff2htmlUi = new Diff2HtmlUI(targetElement, diffString, configuration);
        diff2htmlUi.draw();
        diff2htmlUi.highlightCode();
        var fileName = document.getElementsByClassName('d2h-file-name');
        for (let i = 0; i < fileName.length; i++) {
            ;fileName[1].innerHTML = '<div class="div-item"><div>当前文件：${sourceFile}</div><div>对比文件：${targetFile}</div></div>';
        }
        ;
    });
</script>
<body>
<div id="myDiffElement"></div>
</body>
</html>