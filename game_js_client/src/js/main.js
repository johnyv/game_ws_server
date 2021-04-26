require('../../proto/Protocol_pb');
var $ = require('jquery');

var websocket;

function conn(url) {
    websocket = new WebSocket(url);
    websocket.onopen = function () {
        console.info("打开websocket.");
        $('#Open_Button').attr("disabled", "disabled");
        $('#Send_Button').attr("disabled", null);
        $('#SendBin_Button').attr("disabled", null);
        $('#Close_Button').attr("disabled", null);
        $('#Reg_Button').attr("disabled", null);
        $('#Send_Group_Button').attr("disabled", null);
    };
    websocket.onclose = function () {
        console.info("关闭websocket.");
        $('#Open_Button').attr("disabled", null);
        $('#Send_Button').attr("disabled", "disabled");
        $('#SendBin_Button').attr("disabled", "disabled");
        $('#Close_Button').attr("disabled", "disabled");
        $('#Reg_Button').attr("disabled", "disabled");
        $('#Send_Group_Button').attr("disabled", "disabled");
    };
    websocket.onerror = function () {
        console.info("websocket error");
    };
    websocket.onmessage = function (e) {
        var data = e.data;
        var type = typeof data;
        if (type === 'string') {
            ///alert(data);
            $('#server').prepend(data + '<br /> ');
        } else if (type === 'object') {
            if (data instanceof Blob) {
                var reader = new FileReader();
                reader.addEventListener("loadend", function () {
                    // reader.result contains the contents of blob as a typed array
                    var bytes = reader.result;
                    var l = new DataView(bytes.slice(0,4));
                    var length = l.getInt32(0);
                    console.log("length:"+length);
                    var c = new DataView(bytes.slice(4,8));
                    var code = c.getInt32(0);
                    console.log("code:"+code);
                    switch (code){
                        case 1001:
                            var message = proto.protocol.Player.deserializeBinary(bytes.slice(8));
                            $('#server').prepend(message.getId() + '-->' + message.getName() + '-->' + message.getEntertime() + '<br /> ');
                            break;
                        case 1002:
                            var message = proto.protocol.HeartBeat.deserializeBinary(bytes.slice(8));
                            $('#server').prepend('time-->' + message.getSystemCurrtime() + '<br /> ');
                            break;
                        default:
                            break;
                    }
                    // console.log(bytes);
                });
                reader.readAsArrayBuffer(data);
            } else if (data instanceof ArrayBuffer) {
                console.info('??');
            }
        }
    };
}

function send(message) {
    // reqtime = reqtime +1;
    var msg = new proto.protocol.HeartBeat();
    var d = new Date();

    msg.setSystemCurrtime(d.getTime());

    let bytes = msg.serializeBinary();
    console.log(bytes);

    let buf = new ArrayBuffer(bytes.length + 8);

    let b = new DataView(buf);
    b.setInt32(0,bytes.length + 8)
    b.setUint32(4, 1002);


    for (let i = 0; i < bytes.length; i++) {
        b.setUint8(i + 8, bytes[i]);
    }
    websocket.send(b);
}

function sendBin(userid, txt) {
    // reqtime = reqtime +1;
    var message = new proto.protocol.Player();
    // message.setType(proto.WSMessage.MsgType.MSG);
    // message.setMid(reqtime);
    message.setId(1);
    message.setName('用户名');
    message.setEntertime(0);
    let bytes = message.serializeBinary();
    console.log(bytes);

    let buf = new ArrayBuffer(bytes.length + 8);

    let b = new DataView(buf);
    b.setInt32(0,bytes.length + 8)
    b.setUint32(4, 1001);


    for (let i = 0; i < bytes.length; i++) {
        b.setUint8(i + 8, bytes[i]);
    }
    websocket.send(b);
}

function reg(groupName) {
    // reqtime = reqtime +1;
    // var message = new proto.WSMessage();
    // message.setType(proto.WSMessage.MsgType.REG);
    // message.setMid(reqtime);
    // message.setUid(uid);
    // message.setTId(groupName);
    // bytes = message.serializeBinary();
    // websocket.send(bytes);
}

function broadCast(groupName, txt) {
    // reqtime = reqtime +1;
    // var message = new proto.WSMessage();
    // message.setType(proto.WSMessage.MsgType.GROUP);
    // message.setMid(reqtime);
    // message.setUid(uid);
    // message.setTId(groupName);
    // message.setTxt(txt);
    // bytes = message.serializeBinary();
    // websocket.send(bytes);
}

var heartCheck = {
    timeout: 60000,//60ms
    timeoutObj: null,
    serverTimeoutObj: null,
    reset: function(){
        clearTimeout(this.timeoutObj);
        clearTimeout(this.serverTimeoutObj);
        this.start();
    },
    start: function(){
        var self = this;
        this.timeoutObj = setTimeout(function(){
            websocket.send("HeartBeat");
            self.serverTimeoutObj = setTimeout(function(){
                //如果onclose会执行reconnect，我们执行ws.close()就行了.如果直接执行reconnect 会触发onclose导致重连两次
                websocket.close();
            }, self.timeout)
        }, this.timeout)
    },
}

function closeWebsocket() {
    websocket.close();
}

$("#Send_Button").click(function () {
    var text = $("#send").val();
    if (text === undefined || text === null) {
        text = "nihao";
    }
    send(text);
    $("#send").val('');
});

$('#SendBin_Button').click(function () {
    var tid = $("#tid").val();
    var txt = $("#txt").val();
    sendBin(tid, txt);
    $("#txt").val('');
});

$('#Reg_Button').click(function () {
    var groupName = $("#reg").val();
    reg(groupName);
    $("#reg").val('');
});

$('#Send_Group_Button').click(function () {
    var group = $("#group").val();
    var group_txt = $("#group_txt").val();
    broadCast(group, group_txt);
    $("#group").val('');
    $("#group_txt").val('');
});

$('#Clear_Button').click(function () {
    $('#server').html('');
});

$('#Open_Button').click(function () {
    var uid = $('#uid').val();
    var pwd = $('#pwd').val();
    var url = "ws://127.0.0.1:8090/websocket";
    conn(url);
});

$('#Close_Button').click(function () {
    alert('关闭');
    closeWebsocket();
});