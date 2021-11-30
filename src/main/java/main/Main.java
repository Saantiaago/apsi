package main;

import main.AssistantClasses.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    public static double systemTime = 0.0;
    public static double alpha = 1.0;
    public static double beta = 1.3;
    public static double poissonLambda = 0.4;
    public static int countSources;
    public static int countBuffers;
    public static int countDevices;
    public static int countRequests;
    public static boolean generateIsReady = false;
    public static boolean step = false;

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Select mode: a - auto, s - step by step: ");
        String ch = scanner.nextLine();
        if (ch.equals("s"))
            step = true;
        System.out.print("Insert count of sources: ");
        countSources = scanner.nextInt();
        System.out.print("Insert count of buffers: ");
        countBuffers = scanner.nextInt();
        System.out.print("Insert count of devices: ");
        countDevices = scanner.nextInt();
        System.out.print("Insert count of requests: ");
        countRequests = scanner.nextInt();

        System.out.println("|------------------------------------------|\n| Events            |                      |\n" +
                "|---------+---------+----------+-----+-----|\n| Event   | Time    | Action   |reqC |refC |\n|---------+---------+----------+-----+-----|");

        ArrayList<Source> sources = new ArrayList<Source>(countSources);
        for (int i = 0; i < countSources; i++)
        {
            sources.add(new Source(i + 1));
        }

        ArrayList<Buffer> buffers = new ArrayList<>(countBuffers);
        for (int i = 0; i < countBuffers; i++)
        {
            buffers.add(new Buffer(i + 1));
        }

        ArrayList<Device> devices = new ArrayList<>(countDevices);
        for (int i = 0; i < countDevices; i++)
        {
            devices.add(new Device(i + 1));
        }

        ArrayList<Source.Request> requestList = new ArrayList<>();
        for (Source s : sources)
            requestList.add(s.generate());

        Executor executor = new Executor();

        while (Source.getCountAllRequest() != countRequests) {

            double minTime = requestList.get(0).gettGiner();
            Source.Request newRequest = requestList.get(0);
            int numberSource = requestList.get(0).getSourceNumber();
            int position = 0;

            for (int i = 0; i < requestList.size(); i++) {
                if (requestList.get(i).gettGiner() < minTime) {
                    minTime = requestList.get(i).gettGiner();
                    newRequest = requestList.get(i);
                    numberSource = requestList.get(i).getSourceNumber();
                    position = i;
                }
            }


            systemTime = newRequest.gettGiner();

            requestList.remove(position);
            requestList.add(sources.get(numberSource-1).generate());

            for (Buffer b : buffers) {
                if (b.isEmpty()) {
                    b.add(newRequest, numberSource);
                    systemTime = b.getTimeAdd();
                    break;
                }
            }

            if (!newRequest.isInBuffer()) {

                newRequest.setInRefusal(true, newRequest.getSourceNumber());
                sources.get(newRequest.getSourceNumber() - 1).setCountRefusal();
                Source.Request.setCountRefusal();
                System.out.println("\nRefuse!\n");
            }

            executor.toDevice(buffers, devices, requestList, sources);
            if (step) {
                String st = scanner.nextLine();
            }
        }


        System.out.println("Devices " + countDevices + " Buffers " + countBuffers + " Lambda Of Poiison " + poissonLambda +"\n");
        System.out.println("|-----------------------------------------------|\n|results                                        |\n" +
                "|-----+----+--------+--------+--------+---------|\n| src |reqs| refuse | stayT  | waitT  |serviceT |\n|-----+----+--------+--------+--------+---------|");

        for (Source s : sources) {

            String reqsCount = new DecimalFormat("#0").format(s.getCountRequest());
            String refusePercent = new DecimalFormat("#0.000").format((double) s.getCountRefusal() / s.getCountRequest());
            String stayTime = new DecimalFormat("#0.000").format((s.getTimeServiceForSource() / s.getCountRequest() + s.getTimeBufferForService() / s.getCountRequest()));
            String waitTime = new DecimalFormat("#0.000").format((s.getTimeServiceForSource() / s.getCountRequest()));
            String serviceTime = new DecimalFormat("#0.000").format((s.getTimeBufferForService() / s.getCountRequest()));

            System.out.println("| S" + s.getNumber() + "  | " + reqsCount + " |  " + refusePercent + " |  " + stayTime
                    + " |  " + waitTime + " |  " + serviceTime + "  |\n|-----+----+--------+--------+--------+---------|");
        }
        System.out.println("|device stats    |\n|----------------|\n|device | usage  |\n|-------+--------|");

        for (Device d : devices) {
            String str = "D" + d.getNumber();
            String formattedTableK = new DecimalFormat("#0.000").format(d.getTimeInDevice() / systemTime);
            System.out.println("|   " + str + "  | " + formattedTableK + "  |\n|-------+--------|");
        }

        for (Buffer b : buffers)
            b.clear();
        for (Device d : devices)
            d.clear();
        for (Source s : sources)
            s.clear();
    }
}