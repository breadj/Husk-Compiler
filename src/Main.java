import HuskAST.*;
import gen.HuskLexer;
import gen.HuskParser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.FileWriter;
import java.io.IOException;

import static org.antlr.v4.runtime.CharStreams.fromFileName;

public class Main {

    private final static String INPUT_DIR = "input/";
    private final static String OUTPUT_DIR = "output/";
    private final static String EXT = ".hk";

    public static void main(String[] args) throws IOException {
        String filename = "customtypes";

        CharStream cs = fromFileName(INPUT_DIR + filename + EXT);
        HuskLexer lexer = new HuskLexer(cs);
        CommonTokenStream ts = new CommonTokenStream(lexer);
        HuskParser parser = new HuskParser(ts);

        HuskAST.StartNode ast = (HuskAST.StartNode)HuskASTBuilder.buildAST(parser.start());
        if (ast == null || SemanticAnalysis.analyse(ast)) {
            System.out.println("Catastrophic failure, forfeiting .py file creation.");
            System.exit(1);
        }

        String str = HuskToPython.generate(ast);
        // for quick debugging
        System.out.println(str);

        try (FileWriter writer = new FileWriter(OUTPUT_DIR + filename + ".py", false)) {
            writer.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
