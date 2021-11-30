package main.AssistantClasses;

import main.Main;

import java.text.DecimalFormat;

public class Buffer
{
    private static double allTime;
    private static int countRequest;
    private Source.Request request;
    private int numberSource;
    private double timeAdd;
    private double timeOut;
    private double tForSource;
    private int number;

    public Buffer(int number)
    {
        this.number = number;
    }

    public void add(Source.Request request, int numberSource)
    {
        this.request = request;
        this.numberSource = numberSource;
        timeAdd = Main.systemTime;
        request.setInBuffer(true);
        countRequest++;

        String action = "new " + numberSource + "." + request.getNumber();
        String forTableAddTime = new DecimalFormat("#000.000").format(timeAdd);

        System.out.println("|    B" + getNumber() +"   |  " + forTableAddTime + " | " + action + " | --- |" + " --- |\n" +
                "|---------|---------|----------|-----|-----|");
    }

    public void delete()
    {
        System.out.println("Request " + numberSource + "." + request.getNumber() + " delete from Buffer #" + number);
        String forTableAddTime = new DecimalFormat("#000.000").format(timeAdd);
        String action = "del " + numberSource + "." + request.getNumber();
        System.out.println("|    B" + getNumber() +"   |  " + forTableAddTime + " | " + action + " | --- |" + " --- |\n" +
                "|---------|---------|----------|-----|-----|");

        action = "w8  ";
        forTableAddTime = new DecimalFormat("#000.000").format(timeAdd);
        System.out.println("|    B" + getNumber() +"   |  " + forTableAddTime + " | " + action + " | --- |" + " --- |\n" +
                "|---------|---------|----------|-----|-----|");

        request.setInBuffer(false);
        timeOut = Main.systemTime;
        allTime += timeOut - timeAdd;
        tForSource = timeOut - timeAdd;
        request = null;
    }

    public boolean isEmpty()
    {
        return request == null;
    }

    public double gettForSource()
    {
        return tForSource;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public void clear()
    {
        request = null;
        numberSource = 0;
        timeAdd = 0;
        timeOut = 0;
//        number = 0;
//        allTime = 0;
//        countRequest = 0;
        tForSource = 0;
    }

    public Buffer(Buffer buffer)
    {
        if (buffer.isEmpty())
        {
            request = null;
        }
        else
        {
            request = buffer.getRequest();
        }
        numberSource = buffer.getNumberSource();
        timeAdd = buffer.getTimeAdd();
        timeOut = buffer.getTimeOut();
        number = buffer.getNumber();
        tForSource = buffer.gettForSource();
    }

    public static int getCountRequest()
    {
        return countRequest;
    }

    public static double getAllTime()
    {
        return allTime;
    }

    public static void setAllTime(double allTime)
    {
        Buffer.allTime = allTime;
    }

    public Source.Request getRequest()
    {
        return request;
    }

    public void setRequest(Source.Request request)
    {
        this.request = request;
    }

    public int getNumberSource()
    {
        return numberSource;
    }

    public void setNumberSource(int numberSource)
    {
        this.numberSource = numberSource;
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
}
