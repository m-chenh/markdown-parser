//https://howtodoinjava.com/java/io/java-read-file-to-string-examples/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MarkdownParse {

    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        // find the next [, then find the ], then find the (, then read link upto next )
        int currentIndex = 0;
        while(currentIndex < markdown.length() && markdown.indexOf("[", currentIndex) != -1) {
            int openBracket = markdown.indexOf("[", currentIndex);
            int closeBracket = markdown.indexOf("]", openBracket);
            int openParen = markdown.indexOf("(", closeBracket);
            int closeParen = markdown.indexOf(")", openParen);
            if (openParen == -1 || closeParen == -1) 
            {
                break;
            }
            else if(closeBracket+1 < markdown.length())
                if (markdown.charAt(closeBracket+1) != '(')
                {
                    currentIndex = markdown.indexOf("[", closeBracket);
                }
            else
            {
                currentIndex = closeParen + 1;
                if(openBracket-1 >= 0)
                {
                    if(markdown.charAt(openBracket-1) != '!')
                    {
                        toReturn.add(markdown.substring(openParen + 1, closeParen));
                    }
                }
                else if (openBracket == 0)
                    toReturn.add(markdown.substring(openParen + 1, closeParen));
            }
        }

        return toReturn;
    }


    public static void main(String[] args) throws IOException {
        Path fileName = Path.of(args[0]);
        String content = Files.readString(fileName);
        ArrayList<String> links = getLinks(content);
        // System.out.println("hi");
	System.out.println(links);
    }
}
