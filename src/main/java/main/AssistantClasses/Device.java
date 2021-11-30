package main.AssistantClasses;

import main.Main;

import java.text.DecimalFormat;

public class Device
{
    private static int countRequest = 0;
    private static double allTime;

    private static final double alpha = Main.alpha;
    private static final double beta = Main.beta;

    private Source.Request request;
    private int number;
    private double timeEmpty;
    private double timeAdd;
    private double timeOut;
    private double timeInDevice;
    private double timeToTreatment;
    private double tForSource;
    private int numberSource;

    public Device(int number)
    {
        this.number = number;
    }

    public void add(Source.Request request, int numberSource)
    {
        this.request = request;
        this.numberSource = numberSource;
        request.setInDevice(true);
        timeAdd = Main.systemTime;
        timeEmpty = timeAdd - timeOut;

        String forTableAddTime = new DecimalFormat("#000.000").format(timeAdd);
        String action = "new " + numberSource + "." + request.getNumber();

        System.out.println("|    D" + getNumber() +"   |  " + forTableAddTime + " | " + action + " | --- |" + " --- |\n" +
                "|---------|---------|----------|-----|-----|");
        treatment();
    }

    public void delete()
    {
        String forTableAddTime = new DecimalFormat("#000.000").format(timeAdd);
        String action = "del " + numberSource + "." + request.getNumber();
        System.out.println("|    D" + getNumber() +"   |  " + forTableAddTime + " | " + action + " | --- |" + " --- |\n" +
                "|---------|---------|----------|-----|-----|");

        action  = "w8  " + numberSource + "." + request.getNumber();
        forTableAddTime = new DecimalFormat("#000.000").format(timeAdd);
        System.out.println("|    D" + getNumber() +"   |  " + forTableAddTime + " | " + action + " | --- |" + " --- |\n" +
                "|---------|---------|----------|-----|-----|");

        timeOut = Main.systemTime;
        timeInDevice += timeOut - timeAdd;
        allTime += timeOut - timeAdd;
        tForSource = timeOut - timeAdd;
        countRequest++;
        request.setInDevice(false);
        request = null;
        numberSource = 0;
    }

    public void clear()
    {
        this.request = null;
        this.number = 0;
        this.timeEmpty = 0;
        this.timeAdd = 0;
        this.timeOut = 0;
        this.timeInDevice = 0;
        this.timeToTreatment = 0;
        this.tForSource = 0;
        this.numberSource = 0;
        countRequest = 0;
        allTime = 0;
    }

    public boolean isEmpty()
    {
       return request == null;
    }

    public void treatment()
    {
          timeToTreatment = timeAdd + (beta - alpha) * Math.random() + alpha;
    }

    public double gettForSource()
    {
        return tForSource;
    }

    public int getNumberSource()
    {
        return numberSource;
    }

    public double getTimeToTreatment()
    {
        return timeToTreatment;
    }

    public void setTimeToTreatment(double timeToTreatment)
    {
        this.timeToTreatment = timeToTreatment;
    }

    public static int getCountRequest()
    {
        return countRequest;
    }

    public static void setCountRequest(int countRequest)
    {
        Device.countRequest = countRequest;
    }

    public static double getAllTime()
    {
        return allTime;
    }

    public static void setAllTime(double allTime)
    {
        Device.allTime = allTime;
    }

    public Source.Request getRequest()
    {
        return request;
    }

    public void setRequest(Source.Request request)
    {
        this.request = request;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public double getTimeEmpty()
    {
        return timeEmpty;
    }

    public void setTimeEmpty(double timeEmpty)
    {
        this.timeEmpty = timeEmpty;
    }

    public double getTimeAdd()
    {
        return timeAdd;
    }

    public void setTimeAdd(double timeAdd)
    {
        this.timeAdd = timeAdd;
    }

    public double getTimeOut()
    {
        return timeOut;
    }

    public void setTimeOut(double timeOut)
    {
        this.timeOut = timeOut;
    }

    public double getTimeInDevice()
    {
        return timeInDevice;
    }

    public void setTimeInDevice(double timeInDevice)
    {
        this.timeInDevice = timeInDevice;
    }
}
