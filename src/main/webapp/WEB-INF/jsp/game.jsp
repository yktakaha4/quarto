<%@page import="java.util.UUID"%>
<%@page import="org.apache.commons.text.StringEscapeUtils"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
  session="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<title>quarto</title>

<link rel="stylesheet"
  href="//meyerweb.com/eric/tools/css/reset/reset201101.css">
<link rel="stylesheet"
  href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="styles/game.css">

<script src="//code.jquery.com/jquery-3.2.1.min.js"></script>
<script
  src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="scripts/game.js"></script>
</head>
<body>
  <div class="container">
    <div class="row">
      <div class="col-xs-12">
        <h1 class="description"></h1>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-6 col-xs-12">
        <div class="row row-eq-height piece-container"></div>
      </div>
      <div class="col-sm-6 col-xs-12 board-wrapper">
        <img id="board" src="./images/QuartoBoard.png"
          class="img-responsive board">
      </div>
    </div>
    <div class="row">
      <div class="col-xs-12 text-right">
        <a class="btn btn-lg btn-danger" href="#" role="button" style="display: none;">もう一度対戦</a>
        <a class="btn btn-lg btn-primary" href="./" role="button">退出する</a>
      </div>
    </div>
  </div>
  <div class="template" style="display: none;">
    <div class="stack-piece">
      <div class="col-xs-2 piece-wrapper">
        <img class="img-responsive piece">
      </div>
    </div>
    <div class="board-piece">
      <img class="img-responsive piece">
    </div>
  </div>
  <div class="modal fade room-modal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">部屋名を入力してください</h5>
        </div>
        <div class="modal-body">
          <form>
            <div class="form-group">
              <input type="text" class="form-control room-name"
                maxlength="50">
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary entering-room">入室する</button>
        </div>
      </div>
    </div>
  </div>
  <div class="modal fade error-modal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">エラー</h5>
        </div>
        <div class="modal-body">
          <p class="error-message"></p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-dismiss="modal">閉じる</button>
        </div>
      </div>
    </div>
  </div>
  <script type="text/javascript">
    quarto.property.player = "<%=StringEscapeUtils.escapeEcmaScript(UUID.randomUUID().toString())%>";
  </script>
</body>
</html>