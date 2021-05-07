import * as $protobuf from "protobufjs";
/** Namespace protocol. */
export namespace protocol {

    /** Properties of a Player. */
    interface IPlayer {

        /** Player id */
        id?: (number|null);

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
        public id: number;

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

    /** Properties of a HeartBeat. */
    interface IHeartBeat {

        /** HeartBeat systemCurrtime */
        systemCurrtime?: (number|Long|null);
    }

    /** Represents a HeartBeat. */
    class HeartBeat implements IHeartBeat {

        /**
         * Constructs a new HeartBeat.
         * @param [properties] Properties to set
         */
        constructor(properties?: protocol.IHeartBeat);

        /** HeartBeat systemCurrtime. */
        public systemCurrtime: (number|Long);

        /**
         * Creates a new HeartBeat instance using the specified properties.
         * @param [properties] Properties to set
         * @returns HeartBeat instance
         */
        public static create(properties?: protocol.IHeartBeat): protocol.HeartBeat;

        /**
         * Encodes the specified HeartBeat message. Does not implicitly {@link protocol.HeartBeat.verify|verify} messages.
         * @param message HeartBeat message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encode(message: protocol.IHeartBeat, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Encodes the specified HeartBeat message, length delimited. Does not implicitly {@link protocol.HeartBeat.verify|verify} messages.
         * @param message HeartBeat message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encodeDelimited(message: protocol.IHeartBeat, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Decodes a HeartBeat message from the specified reader or buffer.
         * @param reader Reader or buffer to decode from
         * @param [length] Message length if known beforehand
         * @returns HeartBeat
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): protocol.HeartBeat;

        /**
         * Decodes a HeartBeat message from the specified reader or buffer, length delimited.
         * @param reader Reader or buffer to decode from
         * @returns HeartBeat
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): protocol.HeartBeat;

        /**
         * Verifies a HeartBeat message.
         * @param message Plain object to verify
         * @returns `null` if valid, otherwise the reason why it is not
         */
        public static verify(message: { [k: string]: any }): (string|null);

        /**
         * Creates a HeartBeat message from a plain object. Also converts values to their respective internal types.
         * @param object Plain object
         * @returns HeartBeat
         */
        public static fromObject(object: { [k: string]: any }): protocol.HeartBeat;

        /**
         * Creates a plain object from a HeartBeat message. Also converts values to other types if specified.
         * @param message HeartBeat
         * @param [options] Conversion options
         * @returns Plain object
         */
        public static toObject(message: protocol.HeartBeat, options?: $protobuf.IConversionOptions): { [k: string]: any };

        /**
         * Converts this HeartBeat to JSON.
         * @returns JSON object
         */
        public toJSON(): { [k: string]: any };
    }
}
