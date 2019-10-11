import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Shell {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        String actualPath = System.getProperty("user.dir");
        List<String> history = new ArrayList<>();

        while (true) {
            System.setProperty("user.dir", actualPath);
            System.out.print(actualPath+"/jsh> ");
            String comando = sc.nextLine();
            if (comando.equals("")){
                continue;
            }else if(comando.equalsIgnoreCase("history")){
                int count = 0;
                for (String command: history) {
                    System.out.println(count+": "+command);
                    count++;
                }
                continue;
            }else if(comando.startsWith("cd")){
                history.add(comando);
                if(comando.indexOf("..") > 0){
                    String[] pathNow = actualPath.split("/");
                    pathNow = Arrays.copyOf(pathNow, pathNow.length-1);
                    if(!Files.isDirectory(new File(String.join("/", pathNow)).toPath())){
                        System.out.println("Caminho nao existe");
                        continue;
                    }else{
                        actualPath = String.join("/", pathNow);
                    }
                }else{
                    if(!Files.isDirectory(new File(actualPath + comando.substring(3)).toPath())){
                        System.out.println("Caminho nao existe");
                        continue;
                    }else{
                        actualPath = actualPath + comando.substring(3);
                    }
                }
                System.setProperty("user.dir", actualPath);
                continue;
            }else if(comando.equalsIgnoreCase("!!")){
                comando = history.get(history.size()-1);
            }else if(comando.startsWith("!")){
                try{
                    comando = history.get(Integer.parseInt(comando.substring(1)));
                }catch (Exception e){
                    System.out.println("Comando informado erroneamente");
                    continue;
                }
            }

            List<String> commands =  new ArrayList<String>(Arrays.asList(comando.split(" ")));
//            System.out.println("Run command: "+commands.toString());
            history.add(comando);

            try{
                ProcessBuilder pb = new ProcessBuilder(commands);
                pb.directory(new File(actualPath));
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
            }catch (Exception e){
                System.out.println("Comando informado erroneamente");
            }
        }
    }
}