export default class ProtoMsg{
    public code:number;
    public length:number;
    public dataBytes:Uint8Array;

    public static pack(code:number, uint8Arr:Uint8Array){
        var arrayBuf = new ArrayBuffer(uint8Arr.length+8);
        var databuf = new DataView(arrayBuf);
        databuf.setInt32(0, uint8Arr.length+8);
        databuf.setUint32(4,code);

        for(var i =0; i < uint8Arr.length; i++){
            databuf.setUint8(i+8, uint8Arr[i]);
        }

        return databuf;
    }

    public static unpack(arrBuf:ArrayBuffer):ProtoMsg{
        var lengthBytes = new DataView(arrBuf.slice(0,4));
        var length = lengthBytes.getInt32(0);

        var codeBytes = new DataView(arrBuf.slice(4,8));
        var code = codeBytes.getInt32(0);

        var buf = arrBuf.slice(8);

        var msgPack = new ProtoMsg();
        msgPack.length = length;
        msgPack.code = code;
        msgPack.dataBytes = new Uint8Array(buf);
        return msgPack;
    }
}