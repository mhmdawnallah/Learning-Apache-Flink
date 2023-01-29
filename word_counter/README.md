### Word Counter using Apache Flink

This program uses Apache Flink to perform a word count on a text file. The program demonstrates the use of various Flink transformations, including `map`, `flatMap`, `filter`, and `join`.

### Batch
#### Input
The program takes in different text files as input. The text file should be located in the `data` directory.

#### Output
The program outputs the counts of each word in the text file. The output should be located in the `result` directory

#### How to Run
- Clone the repository
- Navigate to the root of the word_counter directory
- Run the command `mvn clean install`
- Run the command `mvn exec:java -Dexec.mainClass=batch.WordCount`

#### Transformations
- `map`: The map transformation is used to apply a function to each element in the dataset. In this program, it is used to split the line of text into individual words.
- `flatMap`: The flatMap transformation is used to transform each element into zero or more elements. In this program, it is used to split the line of text into individual words and emit each word as a separate element.
- `filter`: The filter transformation is used to filter out elements that do not meet a certain condition. In this program, it is used to filter out words that are less than a certain length.
- `join`: The join transformation is used to combine two or more datasets based on a key. Flink supports several types of joins, including:
    - `inner join`: Only the elements that have a matching key in both datasets are included in the output.
    - `left outer join`: matching elements + non-matching element in the left dataset
    - `right outer join`: matching elements + non-matching elements in the right dataset
    - `full outer join`: matching elements + non-matching elements in both the right and left datasets

### Stream
This explains the functionality of the word counter streaming from a web socket using the following command: nc -l 9999.
#### What does nc -l 9999 do?
nc -l 9999 is a command used to start a network server listening on port 9999 using the nc (netcat) utility. When this command is run, it opens a connection to the specified port (9999) and listens for incoming data. Any data received through this connection can be processed or counted.

#### How  does the word counter streaming part work ?
The word counter counts the number of occurrences of each word starting with "N" that are received through the web socket connection. It splits the incoming data into words and only counts those that start with "N". The final result is a list of words starting with "N" and their respective count, sorted by the frequency of occurrence.

#### Usage
To use the word counter, you need to run the nc -l 9999 command on your terminal. Once the connection is established, you can send data to the server by connecting to the same port (9999) from another terminal. The data will be processed and the word count of words starting with "N" will be displayed in the terminal running the nc -l 9999 command.

#### Conclusion
In summary, the word counter streaming from web socket using the nc -l 9999 command provides a simple and efficient way to count the frequency of words starting with "N" in incoming data from a web socket with the help of apache flink engine. Try it out and see how it works for your specific use case!
### Optimization
In addition to the types of joins, Flink also supports several join hints that can be used to optimize the performance of joins but you need to understand the data very well and its characteristics to make use of these Optimization hints. These include:
- `broadcast`: this hint is used to broadcast one of the input tables to all worker nodes. This can improve performance when one of the tables is small enough to fit in memory.
- `merge`: This hint is used to merge the two input tables on the same worker node before the join is performed. This can improve performance when the tables are already sorted on the join key.
- `shuffle`: This hint is used to redistribute the elements of one or both input tables across all worker nodes based on the join key. This can improve performance when the input tables are not well-partitioned on the join key.

### Note
This is a simple example to understand the basic concepts of Apache Flink, you can improve it by adding more functionality, error handling and testing also I added other joins example in `src/main/java/batch` directory just for the sake of practicing.