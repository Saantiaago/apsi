package main.AssistantClasses;

import main.Main;

import java.text.DecimalFormat;


public class Source
{
    private static int countAllRequest;
    private int number;
    private static final double poissonLambda = Main.poissonLambda;
    private double prevTime;
    private int countRequest = 0;
    private int countRefusal = 0;
    private double timeServiceForSource = 0.0;
    private double timeBufferForService = 0.0;

    public Source(int number)
    {
        prevTime = 0.0;
        this.number = number;
    }

    public Request generate()
    {
        double timeGener = prevTime +  Math.log(1 - Math.random())/(-poissonLambda);

        countRequest++;
        countAllRequest++;
        Request request = new Request(timeGener, countRequest, number);
        prevTime = timeGener;

        String forTableGener = new DecimalFormat("#000.000").format(timeGener);
        String forTableCount = new DecimalFormat("#000").format(countRequest);
        String forTableCountRefus = new DecimalFormat("#000").format(countRefusal);


        System.out.println("|   S" + getNumber() +"    | " + forTableGener + " |generation| " + forTableCount + " | "+ forTableCountRefus+ " |\n" +
                "|---------|---------|----------|-----|-----|");
        System.out.println("|   S" + getNumber() +"    | " + forTableGener + " |wait      | " + forTableCount + " | "+ forTableCountRefus+ " |\n" +
                "|---------|---------|----------|-----|-----|");

        return request;
    }

    public void clear()
    {
        this.number = 0;
        this.prevTime = 0;
        this.countRequest = 0;
        this.countRefusal = 0;
        this.timeServiceForSource = 0;
        this.timeBufferForService = 0;
        countAllRequest = 0;
    }

    public double getTimeServiceForSource()
    {
        return timeServiceForSource;
    }

    public void setTimeServiceForSource(double timeServiceForSource)
    {
        this.timeServiceForSource += timeServiceForSource;
    }

    public double getTimeBufferForService()
    {
        return timeBufferForService;
    }

    public void setTimeBufferForService(double timeBufferForService)
    {
        this.timeBufferForService += timeBufferForService;
    }

    public static int getCountAllRequest()
    {
        return countAllRequest;
    }

    public int getNumber()
    {
        return number;
    }

    public double getPrevTime()
    {
        return prevTime;
    }

    public void setPrevTime(double prevTime)
    {
        this.prevTime = prevTime;
    }

    public int getCountRequest()
    {
        return countRequest;
    }

    public int getCountRefusal()
    {
        return countRefusal;
    }

    public void setCountRefusal()
    {
        countRefusal++;
    }

    public static class Request
    {
        private static int countRefusal = 0;

        private boolean inBuffer = false;
        private boolean inDevice = false;
        private boolean inRefusal = false;

        private double tGiner;
        private static int count = 0;
        private int number;
        private int sourceNumber;

        public Request(double tGiner, int number, int sourceNumber)
        {
            this.tGiner = tGiner;
            this.number = number;
            this.sourceNumber = sourceNumber;
            count++;
        }

        public void setInRefusal(boolean inRefusal, int numberSource)
        {
            this.inRefusal = inRefusal;
        }

        public int getSourceNumber()
        {
            return sourceNumber;
        }

        public static int getCountRefusal()
        {
            return countRefusal;
        }

        public static void setCountRefusal()
        {
            Request.countRefusal++;
        }

        public static int getCount()
        {
            return count;
        }

        public static void setCount(int count)
        {
            Request.count = count;
        }

        public boolean isInBuffer()
        {
            return inBuffer;
        }

        public void setInBuffer(boolean inBuffer)
        {
            this.inBuffer = inBuffer;
        }

        public boolean isInDevice()
        {
            return inDevice;
        }

        public void setInDevice(boolean inDevice)
        {
            this.inDevice = inDevice;
        }

        public boolean isInRefusal()
        {
            return inRefusal;
        }

        public double gettGiner()
        {
            return tGiner;
        }

        public void settGiner(double tGiner)
        {
            this.tGiner = tGiner;
        }

        public int getNumber()
        {
            return number;
        }

        public void setNumber(int number)
        {
            this.number = number;
        }
    }
}
