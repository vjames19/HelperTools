/* ============== HelperTools ==============
 * Initial developer: Michael Kohler <michaelkohler@live.com>
 *
 * =====
 * DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE - Version 2
 *
 * Everyone is permitted to copy and distribute verbatim or modified
 * copies of this license document, and changing it is allowed as long
 * as the name is changed.
 *
 * DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 * TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 * 0. You just DO WHAT THE FUCK YOU WANT TO.
 *
 * =====
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND.
 *
 */
package info.michaelkohler.helpertools.io;

import static info.michaelkohler.helpertools.string.StringHelper.isNullOrEmpty;
import static info.michaelkohler.helpertools.tools.Validator.checkArgument;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * The |FileReader| is responsible to read the content of
 * a whole file. It returns the content as a String.
 *
 * @author Michael Kohler
 * @version 0.0.2
 */
public class FileReader {

    /**
     * Path to the file which needs to be read. This is not just
     * the file name, but the full qualified path.
     */
    private final String path;

    /**
     * Constructor which sets the specified path to the
     * _path variable.
     *
     * @param aPath to the file to be read
     */
    public FileReader(String aPath) {
        checkArgument(!isNullOrEmpty(aPath), "aPath cannot be null or empty");
        this.path = aPath;
    }

    /**
     * Reads the file line after line and returns the whole file
     * content as a String.
     *
     * @return String representation of the text which was read
     * @throws IOException if there was an error opening/accessing/reading the file
     */
    public final String readFile() throws IOException {
        String readText = "";

        BufferedReader br = createBufferedReader();

        String strLine = "";
        while ((strLine = br.readLine()) != null)
            readText += strLine + "\n";

        br.close();
        return readText;
    }
    
	private BufferedReader createBufferedReader() throws IOException {
		java.io.FileReader fReader = new java.io.FileReader(this.path);
		BufferedReader br = new BufferedReader(fReader);
		return br;
	}
    

}
