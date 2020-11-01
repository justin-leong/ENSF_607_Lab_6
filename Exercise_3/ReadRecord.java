/**
 * @author Justin Leong
 * @version 1.0
 * @since November 1, 2020
 */

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ReadRecord {
    
    private ObjectInputStream input;
    
    /**
     *  opens an ObjectInputStream using a FileInputStream
     */
    private void readObjectsFromFile(String name)
    {
        MusicRecord record ;
        
        try
        {
            input = new ObjectInputStream(new FileInputStream( name ) );
        }
        catch ( IOException ioException )
        {
            System.err.println( "Error opening file." );
        }
        
        /* The following loop is supposed to use readObject method of
         * ObjectInputSteam to read a MusicRecord object from a binary file that
         * contains several reords.
         * Loop should terminate when an EOFException is thrown.
         */
        
        try
        {
            while ( true )
            {
                record = (MusicRecord) input.readObject();
                
                System.out.println(record.getYear() + "  " + 
                				   record.getSongName() + "  " + 
                				   record.getSingerName() + "  " + 
                				   record.getPurchasePrice());
            }   // END OF WHILE
        } catch (EOFException e) {
        	// Done reading file
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 

    }           // END OF METHOD 
    
    /**
     * Runs program to read serialized binary file
     * @param args
     */
    public static void main(String [] args)
    {
        ReadRecord d = new ReadRecord();
        d.readObjectsFromFile("mySongs.ser");
    }
}