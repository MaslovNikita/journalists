<%--
  Created by IntelliJ IDEA.
  User: homie
  Date: 21.10.16
  Time: 15:34
  To change this template use File | Settings | File Templates.
--%>
<jsp:useBean id="messageDao" scope="session" class="dao.MessageDao"/>
<jsp:useBean id="user" scope="session" class="model.User"/>

<div id="left-panel">
    <div class="button_left-panel" onclick="goHome()">
        <img src="content/images/icons/user.png">
    </div>
    <div id="message-button_left-panel" class="button_left-panel">
        <a href="/Journalist.ru/inbox">
            <div>${messageDao.countNewIncomingMessage(user.id)}</div>
            <img src="content/images/icons/speech-bubble.png">
        </a>
    </div>
    <div class="button_left-panel">
        <img src="content/images/icons/group.png">
    </div>
</div>
