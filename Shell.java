package com.shell;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Shell
{
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);


        while (true) {

            System.out.print("jsh> ");
            String comando = sc.nextLine();
            if (comando.equals(""))
                continue;
            List<String> commands =  new ArrayList<String>(Arrays.asList(comando.split(" ")));
            //String[] generatedArgs = comando.split(" ");
            //String arg = generatedArgs[0];
            //String params = "";
            //int i = 0;
            //for ( String argInd: generatedArgs ) {
            //    if(i != 0){
            //       params += " "+argInd;
            //    }
            //    i++;
            //}
            System.out.println("Run command: "+commands.toString());
            ProcessBuilder pb = new ProcessBuilder(commands);
            Process process = pb.start();
            // obtain the input stream
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            // read the output of the process
            String line;
            while ( (line = br.readLine()) != null)
                System.out.println(line);
            br.close();
        }
    }
}