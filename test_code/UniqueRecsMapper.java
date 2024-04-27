import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

public class UniqueRecsMapper extends Mapper<Object, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
    private boolean firstrow = true;

    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        if (firstrow) {
            firstrow = false;
            return; 
        }
        String[] columns = line.split(",", -1);
        // Columns of interest: 8 (ARREST_BORO), 11 (AGE_GROUP), 12 (PERP_SEX), 13 (PERP_RACE) in the orignal
        // if you want to add it in one program, just write an if else clause towards input file name
        // Columns of interest: 1 (ARREST_BORO), 2 (AGE_GROUP), 3 (PERP_SEX), 4(PERP_RACE) in the orignal
        String[] columnsInterested = {columns[1], columns[2], columns[3], columns[4]};
        for (String colValue : columnsInterested) {
            word.set(colValue);
            context.write(word, one);
        }
    }
}