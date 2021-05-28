/*eslint-disable block-scoped-var, id-length, no-control-regex, no-magic-numbers, no-prototype-builtins, no-redeclare, no-shadow, no-var, sort-vars*/
"use strict";

var $protobuf = require("protobufjs/minimal");

// Common aliases
var $Reader = $protobuf.Reader, $Writer = $protobuf.Writer, $util = $protobuf.util;

// Exported root namespace
var $root = $protobuf.roots["default"] || ($protobuf.roots["default"] = {});

$root.protocol = (function() {

    /**
     * Namespace protocol.
     * @exports protocol
     * @namespace
     */
    var protocol = {};

    protocol.Player = (function() {

        /**
         * Properties of a Player.
         * @memberof protocol
         * @interface IPlayer
         * @property {number|null} [id] Player id
         * @property {string|null} [name] Player name
         * @property {number|Long|null} [enterTime] Player enterTime
         */

        /**
         * Constructs a new Player.
         * @memberof protocol
         * @classdesc Represents a Player.
         * @implements IPlayer
         * @constructor
         * @param {protocol.IPlayer=} [properties] Properties to set
         */
        function Player(properties) {
            if (properties)
                for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                    if (properties[keys[i]] != null)
                        this[keys[i]] = properties[keys[i]];
        }

        /**
         * Player id.
         * @member {number} id
         * @memberof protocol.Player
         * @instance
         */
        Player.prototype.id = 0;

        /**
         * Player name.
         * @member {string} name
         * @memberof protocol.Player
         * @instance
         */
        Player.prototype.name = "";

        /**
         * Player enterTime.
         * @member {number|Long} enterTime
         * @memberof protocol.Player
         * @instance
         */
        Player.prototype.enterTime = $util.Long ? $util.Long.fromBits(0,0,true) : 0;

        /**
         * Creates a new Player instance using the specified properties.
         * @function create
         * @memberof protocol.Player
         * @static
         * @param {protocol.IPlayer=} [properties] Properties to set
         * @returns {protocol.Player} Player instance
         */
        Player.create = function create(properties) {
            return new Player(properties);
        };

        /**
         * Encodes the specified Player message. Does not implicitly {@link protocol.Player.verify|verify} messages.
         * @function encode
         * @memberof protocol.Player
         * @static
         * @param {protocol.IPlayer} message Player message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        Player.encode = function encode(message, writer) {
            if (!writer)
                writer = $Writer.create();
            if (message.id != null && Object.hasOwnProperty.call(message, "id"))
                writer.uint32(/* id 1, wireType 0 =*/8).uint32(message.id);
            if (message.name != null && Object.hasOwnProperty.call(message, "name"))
                writer.uint32(/* id 2, wireType 2 =*/18).string(message.name);
            if (message.enterTime != null && Object.hasOwnProperty.call(message, "enterTime"))
                writer.uint32(/* id 3, wireType 0 =*/24).uint64(message.enterTime);
            return writer;
        };

        /**
         * Encodes the specified Player message, length delimited. Does not implicitly {@link protocol.Player.verify|verify} messages.
         * @function encodeDelimited
         * @memberof protocol.Player
         * @static
         * @param {protocol.IPlayer} message Player message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        Player.encodeDelimited = function encodeDelimited(message, writer) {
            return this.encode(message, writer).ldelim();
        };

        /**
         * Decodes a Player message from the specified reader or buffer.
         * @function decode
         * @memberof protocol.Player
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @param {number} [length] Message length if known beforehand
         * @returns {protocol.Player} Player
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        Player.decode = function decode(reader, length) {
            if (!(reader instanceof $Reader))
                reader = $Reader.create(reader);
            var end = length === undefined ? reader.len : reader.pos + length, message = new $root.protocol.Player();
            while (reader.pos < end) {
                var tag = reader.uint32();
                switch (tag >>> 3) {
                case 1:
                    message.id = reader.uint32();
                    break;
                case 2:
                    message.name = reader.string();
                    break;
                case 3:
                    message.enterTime = reader.uint64();
                    break;
                default:
                    reader.skipType(tag & 7);
                    break;
                }
            }
            return message;
        };

        /**
         * Decodes a Player message from the specified reader or buffer, length delimited.
         * @function decodeDelimited
         * @memberof protocol.Player
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @returns {protocol.Player} Player
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        Player.decodeDelimited = function decodeDelimited(reader) {
            if (!(reader instanceof $Reader))
                reader = new $Reader(reader);
            return this.decode(reader, reader.uint32());
        };

        /**
         * Verifies a Player message.
         * @function verify
         * @memberof protocol.Player
         * @static
         * @param {Object.<string,*>} message Plain object to verify
         * @returns {string|null} `null` if valid, otherwise the reason why it is not
         */
        Player.verify = function verify(message) {
            if (typeof message !== "object" || message === null)
                return "object expected";
            if (message.id != null && message.hasOwnProperty("id"))
                if (!$util.isInteger(message.id))
                    return "id: integer expected";
            if (message.name != null && message.hasOwnProperty("name"))
                if (!$util.isString(message.name))
                    return "name: string expected";
            if (message.enterTime != null && message.hasOwnProperty("enterTime"))
                if (!$util.isInteger(message.enterTime) && !(message.enterTime && $util.isInteger(message.enterTime.low) && $util.isInteger(message.enterTime.high)))
                    return "enterTime: integer|Long expected";
            return null;
        };

        /**
         * Creates a Player message from a plain object. Also converts values to their respective internal types.
         * @function fromObject
         * @memberof protocol.Player
         * @static
         * @param {Object.<string,*>} object Plain object
         * @returns {protocol.Player} Player
         */
        Player.fromObject = function fromObject(object) {
            if (object instanceof $root.protocol.Player)
                return object;
            var message = new $root.protocol.Player();
            if (object.id != null)
                message.id = object.id >>> 0;
            if (object.name != null)
                message.name = String(object.name);
            if (object.enterTime != null)
                if ($util.Long)
                    (message.enterTime = $util.Long.fromValue(object.enterTime)).unsigned = true;
                else if (typeof object.enterTime === "string")
                    message.enterTime = parseInt(object.enterTime, 10);
                else if (typeof object.enterTime === "number")
                    message.enterTime = object.enterTime;
                else if (typeof object.enterTime === "object")
                    message.enterTime = new $util.LongBits(object.enterTime.low >>> 0, object.enterTime.high >>> 0).toNumber(true);
            return message;
        };

        /**
         * Creates a plain object from a Player message. Also converts values to other types if specified.
         * @function toObject
         * @memberof protocol.Player
         * @static
         * @param {protocol.Player} message Player
         * @param {$protobuf.IConversionOptions} [options] Conversion options
         * @returns {Object.<string,*>} Plain object
         */
        Player.toObject = function toObject(message, options) {
            if (!options)
                options = {};
            var object = {};
            if (options.defaults) {
                object.id = 0;
                object.name = "";
                if ($util.Long) {
                    var long = new $util.Long(0, 0, true);
                    object.enterTime = options.longs === String ? long.toString() : options.longs === Number ? long.toNumber() : long;
                } else
                    object.enterTime = options.longs === String ? "0" : 0;
            }
            if (message.id != null && message.hasOwnProperty("id"))
                object.id = message.id;
            if (message.name != null && message.hasOwnProperty("name"))
                object.name = message.name;
            if (message.enterTime != null && message.hasOwnProperty("enterTime"))
                if (typeof message.enterTime === "number")
                    object.enterTime = options.longs === String ? String(message.enterTime) : message.enterTime;
                else
                    object.enterTime = options.longs === String ? $util.Long.prototype.toString.call(message.enterTime) : options.longs === Number ? new $util.LongBits(message.enterTime.low >>> 0, message.enterTime.high >>> 0).toNumber(true) : message.enterTime;
            return object;
        };

        /**
         * Converts this Player to JSON.
         * @function toJSON
         * @memberof protocol.Player
         * @instance
         * @returns {Object.<string,*>} JSON object
         */
        Player.prototype.toJSON = function toJSON() {
            return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
        };

        return Player;
    })();

    protocol.LoginInfo = (function() {

        /**
         * Properties of a LoginInfo.
         * @memberof protocol
         * @interface ILoginInfo
         * @property {string|null} [id] LoginInfo id
         * @property {string|null} [pwd] LoginInfo pwd
         * @property {number|Long|null} [enterTime] LoginInfo enterTime
         */

        /**
         * Constructs a new LoginInfo.
         * @memberof protocol
         * @classdesc Represents a LoginInfo.
         * @implements ILoginInfo
         * @constructor
         * @param {protocol.ILoginInfo=} [properties] Properties to set
         */
        function LoginInfo(properties) {
            if (properties)
                for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                    if (properties[keys[i]] != null)
                        this[keys[i]] = properties[keys[i]];
        }

        /**
         * LoginInfo id.
         * @member {string} id
         * @memberof protocol.LoginInfo
         * @instance
         */
        LoginInfo.prototype.id = "";

        /**
         * LoginInfo pwd.
         * @member {string} pwd
         * @memberof protocol.LoginInfo
         * @instance
         */
        LoginInfo.prototype.pwd = "";

        /**
         * LoginInfo enterTime.
         * @member {number|Long} enterTime
         * @memberof protocol.LoginInfo
         * @instance
         */
        LoginInfo.prototype.enterTime = $util.Long ? $util.Long.fromBits(0,0,true) : 0;

        /**
         * Creates a new LoginInfo instance using the specified properties.
         * @function create
         * @memberof protocol.LoginInfo
         * @static
         * @param {protocol.ILoginInfo=} [properties] Properties to set
         * @returns {protocol.LoginInfo} LoginInfo instance
         */
        LoginInfo.create = function create(properties) {
            return new LoginInfo(properties);
        };

        /**
         * Encodes the specified LoginInfo message. Does not implicitly {@link protocol.LoginInfo.verify|verify} messages.
         * @function encode
         * @memberof protocol.LoginInfo
         * @static
         * @param {protocol.ILoginInfo} message LoginInfo message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        LoginInfo.encode = function encode(message, writer) {
            if (!writer)
                writer = $Writer.create();
            if (message.id != null && Object.hasOwnProperty.call(message, "id"))
                writer.uint32(/* id 1, wireType 2 =*/10).string(message.id);
            if (message.pwd != null && Object.hasOwnProperty.call(message, "pwd"))
                writer.uint32(/* id 2, wireType 2 =*/18).string(message.pwd);
            if (message.enterTime != null && Object.hasOwnProperty.call(message, "enterTime"))
                writer.uint32(/* id 3, wireType 0 =*/24).uint64(message.enterTime);
            return writer;
        };

        /**
         * Encodes the specified LoginInfo message, length delimited. Does not implicitly {@link protocol.LoginInfo.verify|verify} messages.
         * @function encodeDelimited
         * @memberof protocol.LoginInfo
         * @static
         * @param {protocol.ILoginInfo} message LoginInfo message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        LoginInfo.encodeDelimited = function encodeDelimited(message, writer) {
            return this.encode(message, writer).ldelim();
        };

        /**
         * Decodes a LoginInfo message from the specified reader or buffer.
         * @function decode
         * @memberof protocol.LoginInfo
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @param {number} [length] Message length if known beforehand
         * @returns {protocol.LoginInfo} LoginInfo
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        LoginInfo.decode = function decode(reader, length) {
            if (!(reader instanceof $Reader))
                reader = $Reader.create(reader);
            var end = length === undefined ? reader.len : reader.pos + length, message = new $root.protocol.LoginInfo();
            while (reader.pos < end) {
                var tag = reader.uint32();
                switch (tag >>> 3) {
                case 1:
                    message.id = reader.string();
                    break;
                case 2:
                    message.pwd = reader.string();
                    break;
                case 3:
                    message.enterTime = reader.uint64();
                    break;
                default:
                    reader.skipType(tag & 7);
                    break;
                }
            }
            return message;
        };

        /**
         * Decodes a LoginInfo message from the specified reader or buffer, length delimited.
         * @function decodeDelimited
         * @memberof protocol.LoginInfo
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @returns {protocol.LoginInfo} LoginInfo
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        LoginInfo.decodeDelimited = function decodeDelimited(reader) {
            if (!(reader instanceof $Reader))
                reader = new $Reader(reader);
            return this.decode(reader, reader.uint32());
        };

        /**
         * Verifies a LoginInfo message.
         * @function verify
         * @memberof protocol.LoginInfo
         * @static
         * @param {Object.<string,*>} message Plain object to verify
         * @returns {string|null} `null` if valid, otherwise the reason why it is not
         */
        LoginInfo.verify = function verify(message) {
            if (typeof message !== "object" || message === null)
                return "object expected";
            if (message.id != null && message.hasOwnProperty("id"))
                if (!$util.isString(message.id))
                    return "id: string expected";
            if (message.pwd != null && message.hasOwnProperty("pwd"))
                if (!$util.isString(message.pwd))
                    return "pwd: string expected";
            if (message.enterTime != null && message.hasOwnProperty("enterTime"))
                if (!$util.isInteger(message.enterTime) && !(message.enterTime && $util.isInteger(message.enterTime.low) && $util.isInteger(message.enterTime.high)))
                    return "enterTime: integer|Long expected";
            return null;
        };

        /**
         * Creates a LoginInfo message from a plain object. Also converts values to their respective internal types.
         * @function fromObject
         * @memberof protocol.LoginInfo
         * @static
         * @param {Object.<string,*>} object Plain object
         * @returns {protocol.LoginInfo} LoginInfo
         */
        LoginInfo.fromObject = function fromObject(object) {
            if (object instanceof $root.protocol.LoginInfo)
                return object;
            var message = new $root.protocol.LoginInfo();
            if (object.id != null)
                message.id = String(object.id);
            if (object.pwd != null)
                message.pwd = String(object.pwd);
            if (object.enterTime != null)
                if ($util.Long)
                    (message.enterTime = $util.Long.fromValue(object.enterTime)).unsigned = true;
                else if (typeof object.enterTime === "string")
                    message.enterTime = parseInt(object.enterTime, 10);
                else if (typeof object.enterTime === "number")
                    message.enterTime = object.enterTime;
                else if (typeof object.enterTime === "object")
                    message.enterTime = new $util.LongBits(object.enterTime.low >>> 0, object.enterTime.high >>> 0).toNumber(true);
            return message;
        };

        /**
         * Creates a plain object from a LoginInfo message. Also converts values to other types if specified.
         * @function toObject
         * @memberof protocol.LoginInfo
         * @static
         * @param {protocol.LoginInfo} message LoginInfo
         * @param {$protobuf.IConversionOptions} [options] Conversion options
         * @returns {Object.<string,*>} Plain object
         */
        LoginInfo.toObject = function toObject(message, options) {
            if (!options)
                options = {};
            var object = {};
            if (options.defaults) {
                object.id = "";
                object.pwd = "";
                if ($util.Long) {
                    var long = new $util.Long(0, 0, true);
                    object.enterTime = options.longs === String ? long.toString() : options.longs === Number ? long.toNumber() : long;
                } else
                    object.enterTime = options.longs === String ? "0" : 0;
            }
            if (message.id != null && message.hasOwnProperty("id"))
                object.id = message.id;
            if (message.pwd != null && message.hasOwnProperty("pwd"))
                object.pwd = message.pwd;
            if (message.enterTime != null && message.hasOwnProperty("enterTime"))
                if (typeof message.enterTime === "number")
                    object.enterTime = options.longs === String ? String(message.enterTime) : message.enterTime;
                else
                    object.enterTime = options.longs === String ? $util.Long.prototype.toString.call(message.enterTime) : options.longs === Number ? new $util.LongBits(message.enterTime.low >>> 0, message.enterTime.high >>> 0).toNumber(true) : message.enterTime;
            return object;
        };

        /**
         * Converts this LoginInfo to JSON.
         * @function toJSON
         * @memberof protocol.LoginInfo
         * @instance
         * @returns {Object.<string,*>} JSON object
         */
        LoginInfo.prototype.toJSON = function toJSON() {
            return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
        };

        return LoginInfo;
    })();

    protocol.HBInfo = (function() {

        /**
         * Properties of a HBInfo.
         * @memberof protocol
         * @interface IHBInfo
         * @property {number|Long|null} [systemCurrtime] HBInfo systemCurrtime
         */

        /**
         * Constructs a new HBInfo.
         * @memberof protocol
         * @classdesc Represents a HBInfo.
         * @implements IHBInfo
         * @constructor
         * @param {protocol.IHBInfo=} [properties] Properties to set
         */
        function HBInfo(properties) {
            if (properties)
                for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                    if (properties[keys[i]] != null)
                        this[keys[i]] = properties[keys[i]];
        }

        /**
         * HBInfo systemCurrtime.
         * @member {number|Long} systemCurrtime
         * @memberof protocol.HBInfo
         * @instance
         */
        HBInfo.prototype.systemCurrtime = $util.Long ? $util.Long.fromBits(0,0,false) : 0;

        /**
         * Creates a new HBInfo instance using the specified properties.
         * @function create
         * @memberof protocol.HBInfo
         * @static
         * @param {protocol.IHBInfo=} [properties] Properties to set
         * @returns {protocol.HBInfo} HBInfo instance
         */
        HBInfo.create = function create(properties) {
            return new HBInfo(properties);
        };

        /**
         * Encodes the specified HBInfo message. Does not implicitly {@link protocol.HBInfo.verify|verify} messages.
         * @function encode
         * @memberof protocol.HBInfo
         * @static
         * @param {protocol.IHBInfo} message HBInfo message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        HBInfo.encode = function encode(message, writer) {
            if (!writer)
                writer = $Writer.create();
            if (message.systemCurrtime != null && Object.hasOwnProperty.call(message, "systemCurrtime"))
                writer.uint32(/* id 1, wireType 0 =*/8).int64(message.systemCurrtime);
            return writer;
        };

        /**
         * Encodes the specified HBInfo message, length delimited. Does not implicitly {@link protocol.HBInfo.verify|verify} messages.
         * @function encodeDelimited
         * @memberof protocol.HBInfo
         * @static
         * @param {protocol.IHBInfo} message HBInfo message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        HBInfo.encodeDelimited = function encodeDelimited(message, writer) {
            return this.encode(message, writer).ldelim();
        };

        /**
         * Decodes a HBInfo message from the specified reader or buffer.
         * @function decode
         * @memberof protocol.HBInfo
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @param {number} [length] Message length if known beforehand
         * @returns {protocol.HBInfo} HBInfo
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        HBInfo.decode = function decode(reader, length) {
            if (!(reader instanceof $Reader))
                reader = $Reader.create(reader);
            var end = length === undefined ? reader.len : reader.pos + length, message = new $root.protocol.HBInfo();
            while (reader.pos < end) {
                var tag = reader.uint32();
                switch (tag >>> 3) {
                case 1:
                    message.systemCurrtime = reader.int64();
                    break;
                default:
                    reader.skipType(tag & 7);
                    break;
                }
            }
            return message;
        };

        /**
         * Decodes a HBInfo message from the specified reader or buffer, length delimited.
         * @function decodeDelimited
         * @memberof protocol.HBInfo
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @returns {protocol.HBInfo} HBInfo
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        HBInfo.decodeDelimited = function decodeDelimited(reader) {
            if (!(reader instanceof $Reader))
                reader = new $Reader(reader);
            return this.decode(reader, reader.uint32());
        };

        /**
         * Verifies a HBInfo message.
         * @function verify
         * @memberof protocol.HBInfo
         * @static
         * @param {Object.<string,*>} message Plain object to verify
         * @returns {string|null} `null` if valid, otherwise the reason why it is not
         */
        HBInfo.verify = function verify(message) {
            if (typeof message !== "object" || message === null)
                return "object expected";
            if (message.systemCurrtime != null && message.hasOwnProperty("systemCurrtime"))
                if (!$util.isInteger(message.systemCurrtime) && !(message.systemCurrtime && $util.isInteger(message.systemCurrtime.low) && $util.isInteger(message.systemCurrtime.high)))
                    return "systemCurrtime: integer|Long expected";
            return null;
        };

        /**
         * Creates a HBInfo message from a plain object. Also converts values to their respective internal types.
         * @function fromObject
         * @memberof protocol.HBInfo
         * @static
         * @param {Object.<string,*>} object Plain object
         * @returns {protocol.HBInfo} HBInfo
         */
        HBInfo.fromObject = function fromObject(object) {
            if (object instanceof $root.protocol.HBInfo)
                return object;
            var message = new $root.protocol.HBInfo();
            if (object.systemCurrtime != null)
                if ($util.Long)
                    (message.systemCurrtime = $util.Long.fromValue(object.systemCurrtime)).unsigned = false;
                else if (typeof object.systemCurrtime === "string")
                    message.systemCurrtime = parseInt(object.systemCurrtime, 10);
                else if (typeof object.systemCurrtime === "number")
                    message.systemCurrtime = object.systemCurrtime;
                else if (typeof object.systemCurrtime === "object")
                    message.systemCurrtime = new $util.LongBits(object.systemCurrtime.low >>> 0, object.systemCurrtime.high >>> 0).toNumber();
            return message;
        };

        /**
         * Creates a plain object from a HBInfo message. Also converts values to other types if specified.
         * @function toObject
         * @memberof protocol.HBInfo
         * @static
         * @param {protocol.HBInfo} message HBInfo
         * @param {$protobuf.IConversionOptions} [options] Conversion options
         * @returns {Object.<string,*>} Plain object
         */
        HBInfo.toObject = function toObject(message, options) {
            if (!options)
                options = {};
            var object = {};
            if (options.defaults)
                if ($util.Long) {
                    var long = new $util.Long(0, 0, false);
                    object.systemCurrtime = options.longs === String ? long.toString() : options.longs === Number ? long.toNumber() : long;
                } else
                    object.systemCurrtime = options.longs === String ? "0" : 0;
            if (message.systemCurrtime != null && message.hasOwnProperty("systemCurrtime"))
                if (typeof message.systemCurrtime === "number")
                    object.systemCurrtime = options.longs === String ? String(message.systemCurrtime) : message.systemCurrtime;
                else
                    object.systemCurrtime = options.longs === String ? $util.Long.prototype.toString.call(message.systemCurrtime) : options.longs === Number ? new $util.LongBits(message.systemCurrtime.low >>> 0, message.systemCurrtime.high >>> 0).toNumber() : message.systemCurrtime;
            return object;
        };

        /**
         * Converts this HBInfo to JSON.
         * @function toJSON
         * @memberof protocol.HBInfo
         * @instance
         * @returns {Object.<string,*>} JSON object
         */
        HBInfo.prototype.toJSON = function toJSON() {
            return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
        };

        return HBInfo;
    })();

    protocol.MotionInfo = (function() {

        /**
         * Properties of a MotionInfo.
         * @memberof protocol
         * @interface IMotionInfo
         * @property {number|null} [uid] MotionInfo uid
         * @property {number|null} [x] MotionInfo x
         * @property {number|null} [y] MotionInfo y
         */

        /**
         * Constructs a new MotionInfo.
         * @memberof protocol
         * @classdesc Represents a MotionInfo.
         * @implements IMotionInfo
         * @constructor
         * @param {protocol.IMotionInfo=} [properties] Properties to set
         */
        function MotionInfo(properties) {
            if (properties)
                for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                    if (properties[keys[i]] != null)
                        this[keys[i]] = properties[keys[i]];
        }

        /**
         * MotionInfo uid.
         * @member {number} uid
         * @memberof protocol.MotionInfo
         * @instance
         */
        MotionInfo.prototype.uid = 0;

        /**
         * MotionInfo x.
         * @member {number} x
         * @memberof protocol.MotionInfo
         * @instance
         */
        MotionInfo.prototype.x = 0;

        /**
         * MotionInfo y.
         * @member {number} y
         * @memberof protocol.MotionInfo
         * @instance
         */
        MotionInfo.prototype.y = 0;

        /**
         * Creates a new MotionInfo instance using the specified properties.
         * @function create
         * @memberof protocol.MotionInfo
         * @static
         * @param {protocol.IMotionInfo=} [properties] Properties to set
         * @returns {protocol.MotionInfo} MotionInfo instance
         */
        MotionInfo.create = function create(properties) {
            return new MotionInfo(properties);
        };

        /**
         * Encodes the specified MotionInfo message. Does not implicitly {@link protocol.MotionInfo.verify|verify} messages.
         * @function encode
         * @memberof protocol.MotionInfo
         * @static
         * @param {protocol.IMotionInfo} message MotionInfo message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        MotionInfo.encode = function encode(message, writer) {
            if (!writer)
                writer = $Writer.create();
            if (message.uid != null && Object.hasOwnProperty.call(message, "uid"))
                writer.uint32(/* id 1, wireType 0 =*/8).uint32(message.uid);
            if (message.x != null && Object.hasOwnProperty.call(message, "x"))
                writer.uint32(/* id 2, wireType 5 =*/21).float(message.x);
            if (message.y != null && Object.hasOwnProperty.call(message, "y"))
                writer.uint32(/* id 3, wireType 5 =*/29).float(message.y);
            return writer;
        };

        /**
         * Encodes the specified MotionInfo message, length delimited. Does not implicitly {@link protocol.MotionInfo.verify|verify} messages.
         * @function encodeDelimited
         * @memberof protocol.MotionInfo
         * @static
         * @param {protocol.IMotionInfo} message MotionInfo message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        MotionInfo.encodeDelimited = function encodeDelimited(message, writer) {
            return this.encode(message, writer).ldelim();
        };

        /**
         * Decodes a MotionInfo message from the specified reader or buffer.
         * @function decode
         * @memberof protocol.MotionInfo
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @param {number} [length] Message length if known beforehand
         * @returns {protocol.MotionInfo} MotionInfo
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        MotionInfo.decode = function decode(reader, length) {
            if (!(reader instanceof $Reader))
                reader = $Reader.create(reader);
            var end = length === undefined ? reader.len : reader.pos + length, message = new $root.protocol.MotionInfo();
            while (reader.pos < end) {
                var tag = reader.uint32();
                switch (tag >>> 3) {
                case 1:
                    message.uid = reader.uint32();
                    break;
                case 2:
                    message.x = reader.float();
                    break;
                case 3:
                    message.y = reader.float();
                    break;
                default:
                    reader.skipType(tag & 7);
                    break;
                }
            }
            return message;
        };

        /**
         * Decodes a MotionInfo message from the specified reader or buffer, length delimited.
         * @function decodeDelimited
         * @memberof protocol.MotionInfo
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @returns {protocol.MotionInfo} MotionInfo
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        MotionInfo.decodeDelimited = function decodeDelimited(reader) {
            if (!(reader instanceof $Reader))
                reader = new $Reader(reader);
            return this.decode(reader, reader.uint32());
        };

        /**
         * Verifies a MotionInfo message.
         * @function verify
         * @memberof protocol.MotionInfo
         * @static
         * @param {Object.<string,*>} message Plain object to verify
         * @returns {string|null} `null` if valid, otherwise the reason why it is not
         */
        MotionInfo.verify = function verify(message) {
            if (typeof message !== "object" || message === null)
                return "object expected";
            if (message.uid != null && message.hasOwnProperty("uid"))
                if (!$util.isInteger(message.uid))
                    return "uid: integer expected";
            if (message.x != null && message.hasOwnProperty("x"))
                if (typeof message.x !== "number")
                    return "x: number expected";
            if (message.y != null && message.hasOwnProperty("y"))
                if (typeof message.y !== "number")
                    return "y: number expected";
            return null;
        };

        /**
         * Creates a MotionInfo message from a plain object. Also converts values to their respective internal types.
         * @function fromObject
         * @memberof protocol.MotionInfo
         * @static
         * @param {Object.<string,*>} object Plain object
         * @returns {protocol.MotionInfo} MotionInfo
         */
        MotionInfo.fromObject = function fromObject(object) {
            if (object instanceof $root.protocol.MotionInfo)
                return object;
            var message = new $root.protocol.MotionInfo();
            if (object.uid != null)
                message.uid = object.uid >>> 0;
            if (object.x != null)
                message.x = Number(object.x);
            if (object.y != null)
                message.y = Number(object.y);
            return message;
        };

        /**
         * Creates a plain object from a MotionInfo message. Also converts values to other types if specified.
         * @function toObject
         * @memberof protocol.MotionInfo
         * @static
         * @param {protocol.MotionInfo} message MotionInfo
         * @param {$protobuf.IConversionOptions} [options] Conversion options
         * @returns {Object.<string,*>} Plain object
         */
        MotionInfo.toObject = function toObject(message, options) {
            if (!options)
                options = {};
            var object = {};
            if (options.defaults) {
                object.uid = 0;
                object.x = 0;
                object.y = 0;
            }
            if (message.uid != null && message.hasOwnProperty("uid"))
                object.uid = message.uid;
            if (message.x != null && message.hasOwnProperty("x"))
                object.x = options.json && !isFinite(message.x) ? String(message.x) : message.x;
            if (message.y != null && message.hasOwnProperty("y"))
                object.y = options.json && !isFinite(message.y) ? String(message.y) : message.y;
            return object;
        };

        /**
         * Converts this MotionInfo to JSON.
         * @function toJSON
         * @memberof protocol.MotionInfo
         * @instance
         * @returns {Object.<string,*>} JSON object
         */
        MotionInfo.prototype.toJSON = function toJSON() {
            return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
        };

        return MotionInfo;
    })();

    return protocol;
})();

module.exports = $root;
