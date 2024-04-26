import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

public class CleanMapper extends Mapper<LongWritable, Text, LongWritable, Text> {
    private boolean firstrow = true;
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        
        if (firstrow) {
            firstrow = false;
            return; 
        }
        String[] columns = line.split(",", -1);

        // ensure the row has the correct number of columns
        if (columns.length == 19) { 

            if (!columns[8].isEmpty() && !columns[1].isEmpty() && !columns[8].isEmpty() && !columns[11].isEmpty() && !columns[12].isEmpty() && !columns[13].isEmpty() 
                && !columns[5].isEmpty() && !columns[7].isEmpty()) {
                String cleanedRow = String.join(",",
                    columns[1],  // ARREST_DATE
                    columns[8],  // ARREST_BORO
                    columns[11], // AGE_GROUP
                    columns[12], // PERP_SEX
                    columns[13], // PERP_RACE
                    columns[5], // OFNS_DESC
                    columns[7]  // LAW_CAT_CD
                );
                
                // Write the cleaned row to the context, using the original input key
                context.write(key, new Text(cleanedRow));
            }
        }
    }
}