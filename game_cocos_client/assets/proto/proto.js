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

    protocol.HeartBeat = (function() {

        /**
         * Properties of a HeartBeat.
         * @memberof protocol
         * @interface IHeartBeat
         * @property {number|Long|null} [systemCurrtime] HeartBeat systemCurrtime
         */

        /**
         * Constructs a new HeartBeat.
         * @memberof protocol
         * @classdesc Represents a HeartBeat.
         * @implements IHeartBeat
         * @constructor
         * @param {protocol.IHeartBeat=} [properties] Properties to set
         */
        function HeartBeat(properties) {
            if (properties)
                for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                    if (properties[keys[i]] != null)
                        this[keys[i]] = properties[keys[i]];
        }

        /**
         * HeartBeat systemCurrtime.
         * @member {number|Long} systemCurrtime
         * @memberof protocol.HeartBeat
         * @instance
         */
        HeartBeat.prototype.systemCurrtime = $util.Long ? $util.Long.fromBits(0,0,false) : 0;

        /**
         * Creates a new HeartBeat instance using the specified properties.
         * @function create
         * @memberof protocol.HeartBeat
         * @static
         * @param {protocol.IHeartBeat=} [properties] Properties to set
         * @returns {protocol.HeartBeat} HeartBeat instance
         */
        HeartBeat.create = function create(properties) {
            return new HeartBeat(properties);
        };

        /**
         * Encodes the specified HeartBeat message. Does not implicitly {@link protocol.HeartBeat.verify|verify} messages.
         * @function encode
         * @memberof protocol.HeartBeat
         * @static
         * @param {protocol.IHeartBeat} message HeartBeat message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        HeartBeat.encode = function encode(message, writer) {
            if (!writer)
                writer = $Writer.create();
            if (message.systemCurrtime != null && Object.hasOwnProperty.call(message, "systemCurrtime"))
                writer.uint32(/* id 1, wireType 0 =*/8).int64(message.systemCurrtime);
            return writer;
        };

        /**
         * Encodes the specified HeartBeat message, length delimited. Does not implicitly {@link protocol.HeartBeat.verify|verify} messages.
         * @function encodeDelimited
         * @memberof protocol.HeartBeat
         * @static
         * @param {protocol.IHeartBeat} message HeartBeat message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        HeartBeat.encodeDelimited = function encodeDelimited(message, writer) {
            return this.encode(message, writer).ldelim();
        };

        /**
         * Decodes a HeartBeat message from the specified reader or buffer.
         * @function decode
         * @memberof protocol.HeartBeat
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @param {number} [length] Message length if known beforehand
         * @returns {protocol.HeartBeat} HeartBeat
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        HeartBeat.decode = function decode(reader, length) {
            if (!(reader instanceof $Reader))
                reader = $Reader.create(reader);
            var end = length === undefined ? reader.len : reader.pos + length, message = new $root.protocol.HeartBeat();
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
         * Decodes a HeartBeat message from the specified reader or buffer, length delimited.
         * @function decodeDelimited
         * @memberof protocol.HeartBeat
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @returns {protocol.HeartBeat} HeartBeat
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        HeartBeat.decodeDelimited = function decodeDelimited(reader) {
            if (!(reader instanceof $Reader))
                reader = new $Reader(reader);
            return this.decode(reader, reader.uint32());
        };

        /**
         * Verifies a HeartBeat message.
         * @function verify
         * @memberof protocol.HeartBeat
         * @static
         * @param {Object.<string,*>} message Plain object to verify
         * @returns {string|null} `null` if valid, otherwise the reason why it is not
         */
        HeartBeat.verify = function verify(message) {
            if (typeof message !== "object" || message === null)
                return "object expected";
            if (message.systemCurrtime != null && message.hasOwnProperty("systemCurrtime"))
                if (!$util.isInteger(message.systemCurrtime) && !(message.systemCurrtime && $util.isInteger(message.systemCurrtime.low) && $util.isInteger(message.systemCurrtime.high)))
                    return "systemCurrtime: integer|Long expected";
            return null;
        };

        /**
         * Creates a HeartBeat message from a plain object. Also converts values to their respective internal types.
         * @function fromObject
         * @memberof protocol.HeartBeat
         * @static
         * @param {Object.<string,*>} object Plain object
         * @returns {protocol.HeartBeat} HeartBeat
         */
        HeartBeat.fromObject = function fromObject(object) {
            if (object instanceof $root.protocol.HeartBeat)
                return object;
            var message = new $root.protocol.HeartBeat();
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
         * Creates a plain object from a HeartBeat message. Also converts values to other types if specified.
         * @function toObject
         * @memberof protocol.HeartBeat
         * @static
         * @param {protocol.HeartBeat} message HeartBeat
         * @param {$protobuf.IConversionOptions} [options] Conversion options
         * @returns {Object.<string,*>} Plain object
         */
        HeartBeat.toObject = function toObject(message, options) {
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
         * Converts this HeartBeat to JSON.
         * @function toJSON
         * @memberof protocol.HeartBeat
         * @instance
         * @returns {Object.<string,*>} JSON object
         */
        HeartBeat.prototype.toJSON = function toJSON() {
            return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
        };

        return HeartBeat;
    })();

    protocol.Motion = (function() {

        /**
         * Properties of a Motion.
         * @memberof protocol
         * @interface IMotion
         * @property {number|null} [uid] Motion uid
         * @property {number|null} [x] Motion x
         * @property {number|null} [y] Motion y
         */

        /**
         * Constructs a new Motion.
         * @memberof protocol
         * @classdesc Represents a Motion.
         * @implements IMotion
         * @constructor
         * @param {protocol.IMotion=} [properties] Properties to set
         */
        function Motion(properties) {
            if (properties)
                for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                    if (properties[keys[i]] != null)
                        this[keys[i]] = properties[keys[i]];
        }

        /**
         * Motion uid.
         * @member {number} uid
         * @memberof protocol.Motion
         * @instance
         */
        Motion.prototype.uid = 0;

        /**
         * Motion x.
         * @member {number} x
         * @memberof protocol.Motion
         * @instance
         */
        Motion.prototype.x = 0;

        /**
         * Motion y.
         * @member {number} y
         * @memberof protocol.Motion
         * @instance
         */
        Motion.prototype.y = 0;

        /**
         * Creates a new Motion instance using the specified properties.
         * @function create
         * @memberof protocol.Motion
         * @static
         * @param {protocol.IMotion=} [properties] Properties to set
         * @returns {protocol.Motion} Motion instance
         */
        Motion.create = function create(properties) {
            return new Motion(properties);
        };

        /**
         * Encodes the specified Motion message. Does not implicitly {@link protocol.Motion.verify|verify} messages.
         * @function encode
         * @memberof protocol.Motion
         * @static
         * @param {protocol.IMotion} message Motion message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        Motion.encode = function encode(message, writer) {
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
         * Encodes the specified Motion message, length delimited. Does not implicitly {@link protocol.Motion.verify|verify} messages.
         * @function encodeDelimited
         * @memberof protocol.Motion
         * @static
         * @param {protocol.IMotion} message Motion message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        Motion.encodeDelimited = function encodeDelimited(message, writer) {
            return this.encode(message, writer).ldelim();
        };

        /**
         * Decodes a Motion message from the specified reader or buffer.
         * @function decode
         * @memberof protocol.Motion
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @param {number} [length] Message length if known beforehand
         * @returns {protocol.Motion} Motion
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        Motion.decode = function decode(reader, length) {
            if (!(reader instanceof $Reader))
                reader = $Reader.create(reader);
            var end = length === undefined ? reader.len : reader.pos + length, message = new $root.protocol.Motion();
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
         * Decodes a Motion message from the specified reader or buffer, length delimited.
         * @function decodeDelimited
         * @memberof protocol.Motion
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @returns {protocol.Motion} Motion
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        Motion.decodeDelimited = function decodeDelimited(reader) {
            if (!(reader instanceof $Reader))
                reader = new $Reader(reader);
            return this.decode(reader, reader.uint32());
        };

        /**
         * Verifies a Motion message.
         * @function verify
         * @memberof protocol.Motion
         * @static
         * @param {Object.<string,*>} message Plain object to verify
         * @returns {string|null} `null` if valid, otherwise the reason why it is not
         */
        Motion.verify = function verify(message) {
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
         * Creates a Motion message from a plain object. Also converts values to their respective internal types.
         * @function fromObject
         * @memberof protocol.Motion
         * @static
         * @param {Object.<string,*>} object Plain object
         * @returns {protocol.Motion} Motion
         */
        Motion.fromObject = function fromObject(object) {
            if (object instanceof $root.protocol.Motion)
                return object;
            var message = new $root.protocol.Motion();
            if (object.uid != null)
                message.uid = object.uid >>> 0;
            if (object.x != null)
                message.x = Number(object.x);
            if (object.y != null)
                message.y = Number(object.y);
            return message;
        };

        /**
         * Creates a plain object from a Motion message. Also converts values to other types if specified.
         * @function toObject
         * @memberof protocol.Motion
         * @static
         * @param {protocol.Motion} message Motion
         * @param {$protobuf.IConversionOptions} [options] Conversion options
         * @returns {Object.<string,*>} Plain object
         */
        Motion.toObject = function toObject(message, options) {
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
         * Converts this Motion to JSON.
         * @function toJSON
         * @memberof protocol.Motion
         * @instance
         * @returns {Object.<string,*>} JSON object
         */
        Motion.prototype.toJSON = function toJSON() {
            return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
        };

        return Motion;
    })();

    return protocol;
})();

module.exports = $root;
