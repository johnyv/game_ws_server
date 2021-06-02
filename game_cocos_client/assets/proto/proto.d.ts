import * as $protobuf from "protobufjs";
/** Namespace protocol. */
export namespace protocol {

    /** Properties of a Player. */
    interface IPlayer {

        /** Player id */
        id?: (string|null);

        /** Player name */
        name?: (string|null);

        /** Player enterTime */
        enterTime?: (number|Long|null);
    }

    /** Represents a Player. */
    class Player implements IPlayer {

        /**
         * Constructs a new Player.
         * @param [properties] Properties to set
         */
        constructor(properties?: protocol.IPlayer);

        /** Player id. */
        public id: string;

        /** Player name. */
        public name: string;

        /** Player enterTime. */
        public enterTime: (number|Long);

        /**
         * Creates a new Player instance using the specified properties.
         * @param [properties] Properties to set
         * @returns Player instance
         */
        public static create(properties?: protocol.IPlayer): protocol.Player;

        /**
         * Encodes the specified Player message. Does not implicitly {@link protocol.Player.verify|verify} messages.
         * @param message Player message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encode(message: protocol.IPlayer, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Encodes the specified Player message, length delimited. Does not implicitly {@link protocol.Player.verify|verify} messages.
         * @param message Player message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encodeDelimited(message: protocol.IPlayer, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Decodes a Player message from the specified reader or buffer.
         * @param reader Reader or buffer to decode from
         * @param [length] Message length if known beforehand
         * @returns Player
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): protocol.Player;

        /**
         * Decodes a Player message from the specified reader or buffer, length delimited.
         * @param reader Reader or buffer to decode from
         * @returns Player
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): protocol.Player;

        /**
         * Verifies a Player message.
         * @param message Plain object to verify
         * @returns `null` if valid, otherwise the reason why it is not
         */
        public static verify(message: { [k: string]: any }): (string|null);

        /**
         * Creates a Player message from a plain object. Also converts values to their respective internal types.
         * @param object Plain object
         * @returns Player
         */
        public static fromObject(object: { [k: string]: any }): protocol.Player;

        /**
         * Creates a plain object from a Player message. Also converts values to other types if specified.
         * @param message Player
         * @param [options] Conversion options
         * @returns Plain object
         */
        public static toObject(message: protocol.Player, options?: $protobuf.IConversionOptions): { [k: string]: any };

        /**
         * Converts this Player to JSON.
         * @returns JSON object
         */
        public toJSON(): { [k: string]: any };
    }

    /** Properties of a UserList. */
    interface IUserList {

        /** UserList userList */
        userList?: (protocol.IPlayer[]|null);
    }

    /** Represents a UserList. */
    class UserList implements IUserList {

        /**
         * Constructs a new UserList.
         * @param [properties] Properties to set
         */
        constructor(properties?: protocol.IUserList);

        /** UserList userList. */
        public userList: protocol.IPlayer[];

        /**
         * Creates a new UserList instance using the specified properties.
         * @param [properties] Properties to set
         * @returns UserList instance
         */
        public static create(properties?: protocol.IUserList): protocol.UserList;

        /**
         * Encodes the specified UserList message. Does not implicitly {@link protocol.UserList.verify|verify} messages.
         * @param message UserList message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encode(message: protocol.IUserList, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Encodes the specified UserList message, length delimited. Does not implicitly {@link protocol.UserList.verify|verify} messages.
         * @param message UserList message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encodeDelimited(message: protocol.IUserList, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Decodes a UserList message from the specified reader or buffer.
         * @param reader Reader or buffer to decode from
         * @param [length] Message length if known beforehand
         * @returns UserList
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): protocol.UserList;

        /**
         * Decodes a UserList message from the specified reader or buffer, length delimited.
         * @param reader Reader or buffer to decode from
         * @returns UserList
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): protocol.UserList;

        /**
         * Verifies a UserList message.
         * @param message Plain object to verify
         * @returns `null` if valid, otherwise the reason why it is not
         */
        public static verify(message: { [k: string]: any }): (string|null);

        /**
         * Creates a UserList message from a plain object. Also converts values to their respective internal types.
         * @param object Plain object
         * @returns UserList
         */
        public static fromObject(object: { [k: string]: any }): protocol.UserList;

        /**
         * Creates a plain object from a UserList message. Also converts values to other types if specified.
         * @param message UserList
         * @param [options] Conversion options
         * @returns Plain object
         */
        public static toObject(message: protocol.UserList, options?: $protobuf.IConversionOptions): { [k: string]: any };

        /**
         * Converts this UserList to JSON.
         * @returns JSON object
         */
        public toJSON(): { [k: string]: any };
    }

    /** Properties of a LoginInfo. */
    interface ILoginInfo {

        /** LoginInfo id */
        id?: (string|null);

        /** LoginInfo pwd */
        pwd?: (string|null);

        /** LoginInfo enterTime */
        enterTime?: (number|Long|null);
    }

    /** Represents a LoginInfo. */
    class LoginInfo implements ILoginInfo {

        /**
         * Constructs a new LoginInfo.
         * @param [properties] Properties to set
         */
        constructor(properties?: protocol.ILoginInfo);

        /** LoginInfo id. */
        public id: string;

        /** LoginInfo pwd. */
        public pwd: string;

        /** LoginInfo enterTime. */
        public enterTime: (number|Long);

        /**
         * Creates a new LoginInfo instance using the specified properties.
         * @param [properties] Properties to set
         * @returns LoginInfo instance
         */
        public static create(properties?: protocol.ILoginInfo): protocol.LoginInfo;

        /**
         * Encodes the specified LoginInfo message. Does not implicitly {@link protocol.LoginInfo.verify|verify} messages.
         * @param message LoginInfo message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encode(message: protocol.ILoginInfo, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Encodes the specified LoginInfo message, length delimited. Does not implicitly {@link protocol.LoginInfo.verify|verify} messages.
         * @param message LoginInfo message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encodeDelimited(message: protocol.ILoginInfo, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Decodes a LoginInfo message from the specified reader or buffer.
         * @param reader Reader or buffer to decode from
         * @param [length] Message length if known beforehand
         * @returns LoginInfo
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): protocol.LoginInfo;

        /**
         * Decodes a LoginInfo message from the specified reader or buffer, length delimited.
         * @param reader Reader or buffer to decode from
         * @returns LoginInfo
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): protocol.LoginInfo;

        /**
         * Verifies a LoginInfo message.
         * @param message Plain object to verify
         * @returns `null` if valid, otherwise the reason why it is not
         */
        public static verify(message: { [k: string]: any }): (string|null);

        /**
         * Creates a LoginInfo message from a plain object. Also converts values to their respective internal types.
         * @param object Plain object
         * @returns LoginInfo
         */
        public static fromObject(object: { [k: string]: any }): protocol.LoginInfo;

        /**
         * Creates a plain object from a LoginInfo message. Also converts values to other types if specified.
         * @param message LoginInfo
         * @param [options] Conversion options
         * @returns Plain object
         */
        public static toObject(message: protocol.LoginInfo, options?: $protobuf.IConversionOptions): { [k: string]: any };

        /**
         * Converts this LoginInfo to JSON.
         * @returns JSON object
         */
        public toJSON(): { [k: string]: any };
    }

    /** Properties of a HBInfo. */
    interface IHBInfo {

        /** HBInfo systemCurrtime */
        systemCurrtime?: (number|Long|null);
    }

    /** Represents a HBInfo. */
    class HBInfo implements IHBInfo {

        /**
         * Constructs a new HBInfo.
         * @param [properties] Properties to set
         */
        constructor(properties?: protocol.IHBInfo);

        /** HBInfo systemCurrtime. */
        public systemCurrtime: (number|Long);

        /**
         * Creates a new HBInfo instance using the specified properties.
         * @param [properties] Properties to set
         * @returns HBInfo instance
         */
        public static create(properties?: protocol.IHBInfo): protocol.HBInfo;

        /**
         * Encodes the specified HBInfo message. Does not implicitly {@link protocol.HBInfo.verify|verify} messages.
         * @param message HBInfo message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encode(message: protocol.IHBInfo, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Encodes the specified HBInfo message, length delimited. Does not implicitly {@link protocol.HBInfo.verify|verify} messages.
         * @param message HBInfo message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encodeDelimited(message: protocol.IHBInfo, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Decodes a HBInfo message from the specified reader or buffer.
         * @param reader Reader or buffer to decode from
         * @param [length] Message length if known beforehand
         * @returns HBInfo
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): protocol.HBInfo;

        /**
         * Decodes a HBInfo message from the specified reader or buffer, length delimited.
         * @param reader Reader or buffer to decode from
         * @returns HBInfo
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): protocol.HBInfo;

        /**
         * Verifies a HBInfo message.
         * @param message Plain object to verify
         * @returns `null` if valid, otherwise the reason why it is not
         */
        public static verify(message: { [k: string]: any }): (string|null);

        /**
         * Creates a HBInfo message from a plain object. Also converts values to their respective internal types.
         * @param object Plain object
         * @returns HBInfo
         */
        public static fromObject(object: { [k: string]: any }): protocol.HBInfo;

        /**
         * Creates a plain object from a HBInfo message. Also converts values to other types if specified.
         * @param message HBInfo
         * @param [options] Conversion options
         * @returns Plain object
         */
        public static toObject(message: protocol.HBInfo, options?: $protobuf.IConversionOptions): { [k: string]: any };

        /**
         * Converts this HBInfo to JSON.
         * @returns JSON object
         */
        public toJSON(): { [k: string]: any };
    }

    /** Properties of a MotionInfo. */
    interface IMotionInfo {

        /** MotionInfo uid */
        uid?: (string|null);

        /** MotionInfo x */
        x?: (number|null);

        /** MotionInfo y */
        y?: (number|null);
    }

    /** Represents a MotionInfo. */
    class MotionInfo implements IMotionInfo {

        /**
         * Constructs a new MotionInfo.
         * @param [properties] Properties to set
         */
        constructor(properties?: protocol.IMotionInfo);

        /** MotionInfo uid. */
        public uid: string;

        /** MotionInfo x. */
        public x: number;

        /** MotionInfo y. */
        public y: number;

        /**
         * Creates a new MotionInfo instance using the specified properties.
         * @param [properties] Properties to set
         * @returns MotionInfo instance
         */
        public static create(properties?: protocol.IMotionInfo): protocol.MotionInfo;

        /**
         * Encodes the specified MotionInfo message. Does not implicitly {@link protocol.MotionInfo.verify|verify} messages.
         * @param message MotionInfo message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encode(message: protocol.IMotionInfo, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Encodes the specified MotionInfo message, length delimited. Does not implicitly {@link protocol.MotionInfo.verify|verify} messages.
         * @param message MotionInfo message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encodeDelimited(message: protocol.IMotionInfo, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Decodes a MotionInfo message from the specified reader or buffer.
         * @param reader Reader or buffer to decode from
         * @param [length] Message length if known beforehand
         * @returns MotionInfo
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): protocol.MotionInfo;

        /**
         * Decodes a MotionInfo message from the specified reader or buffer, length delimited.
         * @param reader Reader or buffer to decode from
         * @returns MotionInfo
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): protocol.MotionInfo;

        /**
         * Verifies a MotionInfo message.
         * @param message Plain object to verify
         * @returns `null` if valid, otherwise the reason why it is not
         */
        public static verify(message: { [k: string]: any }): (string|null);

        /**
         * Creates a MotionInfo message from a plain object. Also converts values to their respective internal types.
         * @param object Plain object
         * @returns MotionInfo
         */
        public static fromObject(object: { [k: string]: any }): protocol.MotionInfo;

        /**
         * Creates a plain object from a MotionInfo message. Also converts values to other types if specified.
         * @param message MotionInfo
         * @param [options] Conversion options
         * @returns Plain object
         */
        public static toObject(message: protocol.MotionInfo, options?: $protobuf.IConversionOptions): { [k: string]: any };

        /**
         * Converts this MotionInfo to JSON.
         * @returns JSON object
         */
        public toJSON(): { [k: string]: any };
    }
}
