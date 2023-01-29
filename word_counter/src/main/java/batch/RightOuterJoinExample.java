package batch;

import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.operators.base.JoinOperatorBase;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.utils.ParameterTool;

@SuppressWarnings("serial")
public class RightOuterJoinExample
{
    public static void main(String[] args) throws Exception {

        // set up the execution environment
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        final ParameterTool params = ParameterTool.fromArgs(args);
        // make parameters available in the web interface

        env.getConfig().setGlobalJobParameters(params);

        // 1, Name
        DataSet<Tuple2<Integer, String>> personSet = env.readTextFile(params.get("input1")).           //presonSet = tuple of (1  John)
                map(new TextFilter());

        DataSet<Tuple2<Integer, String>> locationSet = env.readTextFile(params.get("input2")).
                map(new TextFilter());

        // right outer join datasets on person_id
        // joined format will be <id, person_name, state>

        DataSet<Tuple3<Integer, String, String>> joined = personSet.rightOuterJoin(locationSet, JoinOperatorBase.JoinHint.OPTIMIZER_CHOOSES).where(0) .equalTo(0)
                .with(new RightOuterJoin());//.collect();

        joined.writeAsCsv(params.get("output"), "\n", " ");

        env.execute("Right Outer Join Example");
    }

    public static final class TextFilter implements MapFunction<String, Tuple2<Integer, String>>{
        public Tuple2<Integer, String> map(String value)
        {
            String[] words = value.split(",");                                                 // words = [ {1} {John}]
            return new Tuple2<Integer, String>(Integer.parseInt(words[0]), words[1]);
        }
    }

    public static final class RightOuterJoin implements JoinFunction<Tuple2<Integer, String>, Tuple2<Integer, String>, Tuple3<Integer, String, String>>{
        public Tuple3<Integer, String, String> join(Tuple2<Integer, String> person,  Tuple2<Integer, String> location)
        {
            // check for nulls
            if (person == null)
            {
                return new Tuple3<Integer, String, String>(location.f0, "NULL", location.f1);
            }

            return new Tuple3<Integer, String, String>(person.f0,   person.f1,  location.f1);
        }
    }

}
