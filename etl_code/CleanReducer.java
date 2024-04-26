import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

public class CleanReducer extends Reducer<LongWritable, Text, LongWritable, Text> {
    protected void reduce(LongWritable key, Iterable<Text> values, Context context) 
            throws IOException, InterruptedException {
        for (Text value : values) {
            context.write(key, value);
        }
    }
}