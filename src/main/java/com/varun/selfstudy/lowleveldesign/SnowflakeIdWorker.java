package com.varun.selfstudy.lowleveldesign;

/**
 * Twitter_Snowflake<br>
 * The structure of SnowFlake is as follows (each part is separated by -):<br>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000 <br>
 * 1-bit identifier, as the long basic type is signed in Java, the highest bit is the sign bit, positive numbers are 0, negative numbers are 1, so the id is generally positive, the highest bit is 0 <br>
 * 41-bit time intercept (milliseconds), note that the 41-bit time intercept is not a time intercept to store the current time, but the difference between the time intercept (current time intercept - start time intercept)
 * the value obtained), where the start time intercept, generally our id generator to start using the time specified by our program (the following program IdWorker class startTime property). 41-bit time intercept, you can use 69 years, year T = (1L << 41)/(1000L * 60 * 60 * 24 * 365) = 69<br>
 * 10-bit data machine bits that can be deployed in 1024 nodes, including 5-bit datacenterId and 5-bit workerId<br>
 * 12-bit sequential, millisecond counting, 12-bit counting sequence number supports 4096 ID sequential numbers per node per millisecond (same machine, same time cutoff)<br>
 * Add up to exactly 64 bits for a Long type. <br>
 * The advantage of SnowFlake is that the overall self-increasing sorting by time and no ID collision within the whole distributed system (distinguished by data center ID and machine ID), and high efficiency, tested, SnowFlake can generate about 260,000 IDs per second.
 */
public class SnowflakeIdWorker {

    // ==============================Fields===========================================
    /** Start time cutoff (2015-01-01) */
    private final long twepoch = 1420041600000L;

    /* The number of bits occupied by the machine id */
    private final long workerIdBits = 5L;

    /* The number of bits occupied by the data identifier id */
    private final long datacenterIdBits = 5L;

    /* The maximum machine id supported, resulting in 31 (this shift algorithm can quickly calculate the maximum number of decimal digits that can be represented by a few binary digits) */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /* The maximum supported data identifier id, resulting in 31 */
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    /** The number of bits in the id that the sequence occupies */
    private final long sequenceBits = 12L;

    /** The machine ID is shifted 12 bits to the left */
    private final long workerIdShift = sequenceBits;

    /** The data identifier id is shifted to the left by 17 bits (12+5)*/
    private final long datacenterIdShift = sequenceBits + workerIdBits;

            /** The time truncation is shifted to the left by 22 bits (5+5+12) */
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /** Generate the mask for the sequence, here 4095. (0b111111111111=0xfff=4095) */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /** Work machine ID(0~31) */
    private long workerId;

    /** Work machine ID(0~31) */
    private long datacenterId;

    /** Intra-millisecond sequence (0~4095) */
    private long sequence = 0L;

    /** Time cutoff of the last generated ID */
    private long lastTimestamp = -1L;

    //==============================Constructors=====================================
    /**
     * Constructor
     * @param workerId Job ID (0~31)
     * @param datacenterId datacenterId (0~31)
     */
    public SnowflakeIdWorker(long workerId, long datacenterId) {
        this.datacenterId = datacenterId;
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
    }

    // ==============================Methods==========================================
    /**
     * Get the next ID (this method is thread-safe)
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        //If the current time is less than the timestamp of the last ID generation, it means that the system clock is backed off and an exception should be thrown at this time
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //If it was generated at the same time, then perform a sequence within milliseconds
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            // sequence overflow in milliseconds
            if (sequence == 0) {
                //Block until the next millisecond, get the new timestamp
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //Timestamp change, sequence reset in milliseconds
        else {
            sequence = 0L;
        }

        //Time cutoff of the last generated ID
        lastTimestamp = timestamp;

        System.out.println("timestamp = " + Long.toBinaryString(timestamp - twepoch));
        System.out.println("timestamp after left shift = " + Long.toBinaryString((timestamp - twepoch) << timestampLeftShift));
        System.out.println("datacenterId = " + Long.toBinaryString(datacenterId << datacenterIdShift));
        System.out.println("workerId = " + Long.toBinaryString(workerId << workerIdShift));
        System.out.println("Or 1  = " + Long.toBinaryString(((timestamp - twepoch) << timestampLeftShift) //
                | (datacenterId << datacenterIdShift)));

        System.out.println("Or 2  = " + Long.toBinaryString(((timestamp - twepoch) << timestampLeftShift) //
                | (datacenterId << datacenterIdShift) //
                | (workerId << workerIdShift)));

        System.out.println("Or 3  = " + Long.toBinaryString(((timestamp - twepoch) << timestampLeftShift) //
                | (datacenterId << datacenterIdShift) //
                | (workerId << workerIdShift) | sequence));


        System.out.println("Or 4  = " + Long.toBinaryString(((timestamp - twepoch) << timestampLeftShift) //
                + (datacenterId << datacenterIdShift) //
                + (workerId << workerIdShift) | sequence));


        //Shifted and put together by orthogonal operations to form a 64-bit ID
        return ((timestamp - twepoch) << timestampLeftShift) //
                | (datacenterId << datacenterIdShift) //
                | (workerId << workerIdShift) //
                | sequence;
    }

    /**
     * Block to the next millisecond until the new timestamp is obtained
     * @param lastTimestamp Time cutoff of the last generated ID
     * @return currentTimestamp
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * Returns the current time in milliseconds
     * @return current time in milliseconds
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }

    //==============================Test=============================================
    /** TEST */
    public static void main(String[] args) {
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        for (int i = 0; i < 10; i++) {
            long id = idWorker.nextId();
            System.out.println(Long.toBinaryString(id));
            System.out.println(id);
        }
    }
}