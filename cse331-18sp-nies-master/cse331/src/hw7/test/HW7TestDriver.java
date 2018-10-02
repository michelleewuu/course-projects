package hw7.test;

import java.io.*;
import java.util.*;
import hw5.*;
import hw7.*;


/**
 * This class implements a testing driver which reads test scripts
 * from files for your graph ADT and improved MarvelPaths application
 * using Dijkstra's algorithm.
 **/
public class HW7TestDriver {

    public static void main(String args[]) {
    	 try {
             if (args.length > 1) {
                 printUsage();
                 return;
             }

             HW7TestDriver td;

             if (args.length == 0) {
                 td = new HW7TestDriver(new InputStreamReader(System.in),
                                        new OutputStreamWriter(System.out));
             } else {

                 String fileName = args[0];
                 File tests = new File (fileName);

                 if (tests.exists() || tests.canRead()) {
                     td = new HW7TestDriver(new FileReader(tests),
                                            new OutputStreamWriter(System.out));
                 } else {
                     System.err.println("Cannot read from " + tests.toString());
                     printUsage();
                     return;
                 }
             }

             td.runTests();

         } catch (IOException e) {
             System.err.println(e.toString());
             e.printStackTrace(System.err);
         }
    }
    private static void printUsage() {
        System.err.println("Usage:");
        System.err.println("to read from a file: java hw7.test.HW7TestDriver <name of input script>");
        System.err.println("to read from standard in: java hw7.test.HW7TestDriver");
    }

    /** String -> Graph: maps the names of graphs to the actual graph **/
    private final Map<String, LabeledMultiGraph<String,Double>> graphs = 
            new HashMap<String,LabeledMultiGraph<String,Double>>();
    private final PrintWriter output;
    private final BufferedReader input;

    public HW7TestDriver(Reader r, Writer w) {
    	 input = new BufferedReader(r);
         output = new PrintWriter(w);
    }
    
    /**
     * @effects Executes the commands read from the input 
     *          and writes results to the output
     * @throws IOException if the input or output sources encounter an IOException
     **/
    public void runTests() 
    	throws IOException
    {
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            if ((inputLine.trim().length() == 0) ||
                (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            }
            else
            {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if (st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<String>();
                    while (st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }
    
    private void executeCommand(String command, List<String> arguments) {
        try {
            if (command.equals("CreateGraph")) {
                createGraph(arguments);
            } else if (command.equals("AddNode")) {
                addNode(arguments);
            } else if (command.equals("AddEdge")) {
                addEdge(arguments);
            } else if (command.equals("ListNodes")) {
                listNodes(arguments);
            } else if (command.equals("ListChildren")) {
                listChildren(arguments);
            } else if (command.equals("LoadGraph")) {
                loadGraph(arguments);
            } else if (command.equals("FindPath")) {
            	findPath(arguments);
            } else {
                output.println("Unrecognized command: " + command);
            }
        } catch (Exception e) {
            output.println("Exception: " + e.toString());
        }
    }

    private void createGraph(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        graphs.put(graphName, new LabeledMultiGraph<String,Double>());
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to addNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        LabeledMultiGraph<String,Double> g = graphs.get(graphName);
        g.addNode(new Node<String>(nodeName));
        output.println("added node " + nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {
        if (arguments.size() != 4) {
            throw new CommandException("Bad arguments to addEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        double edgeLabel = Double.parseDouble(arguments.get(3));
        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
            Double edgeLabel) {
    	LabeledMultiGraph<String,Double> g = graphs.get(graphName);
    	g.addEdge(edgeLabel, new Node<String>(parentName), new Node<String>(childName));
    	output.println("added edge " + String.format("%.3f",edgeLabel) + " from " + parentName + 
        		" to " + childName + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to listNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
    	LabeledMultiGraph<String,Double> g = graphs.get(graphName);
    	String result = graphName + " contains:";
    	TreeSet<Node<String>> sortNodes = 
        		new TreeSet<Node<String>>(new Comparator<Node<String>>() {
        			public int compare(Node<String> n1, Node<String> n2) {	
        				if (!n1.getData().equals(n2.getData())) {
        					return n1.getData().compareTo(n2.getData());
        				}
        				return 0;		
        			}		
        		});
    	sortNodes.addAll(g.getNodes());
    	for (Node<String> n: sortNodes) {
    		result += " " + n.getData();
    	}
    	output.println(result);
    }

    private void listChildren(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to listChildren: " + arguments);
        }
        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
    	LabeledMultiGraph<String,Double> g = graphs.get(graphName);
    	String result = "the children of " + parentName + " in " + graphName + " are:";
    	TreeSet<Edge<String,Double>> sortEdges =  
    	new TreeSet<Edge<String,Double>>(new Comparator<Edge<String,Double>>() {
			public int compare(Edge<String,Double> e1, Edge<String,Double> e2) {	
				if(!(e1.getChild().equals(e2.getChild()))) {
					return e1.getChild().getData().compareTo(e2.getChild().getData());	
				}
				if (!(e1.getLabel().equals(e2.getLabel()))) {
					return e1.getLabel().compareTo(e2.getLabel());	
				}
				return 0;		
			}		
		});
    	HashSet<Edge<String,Double>> edges = g.getEdges(new Node<String>(parentName));
    	sortEdges.addAll(edges);
    	for (Edge<String,Double> e: sortEdges) {
			result += " " + e.getChild().getData() + "(" + String.format("%.3f",e.getLabel()) +")";
		}
    	output.println(result); 	
    }
    
    private void loadGraph(List<String> arguments) throws Exception {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to loadGraph: " + arguments);
        }
        String graphName = arguments.get(0);
        String filename = arguments.get(1);     
        loadGraph(graphName, filename);
    }
    
    private void loadGraph(String graphName, String filename) throws Exception {
    	filename = "src/hw7/data/" + filename;
        graphs.put(graphName, MarvelPaths2.buildGraph(filename));
        output.println("loaded graph " + graphName);

    }

    private void findPath(List<String> arguments) {
        if (arguments.size() != 3) {
            throw new CommandException("Bad arguments to findPath: " + arguments);
        }
        String graphName = arguments.get(0);
        String node1Name = arguments.get(1).replace('_', ' ');
        String node2Name = arguments.get(2).replace('_', ' ');        
        findPath(graphName, node1Name, node2Name);
    }
    
    private void findPath(String graphName, String start, String end) {
    	LabeledMultiGraph<String, Double> g = graphs.get(graphName);
    	List<Edge<String, Double>> path = MarvelPaths2.minPath(new Node<String>(start), new Node<String>(end), g);
    	String result = "";
    	if (!g.containsNode(new Node<String>(start)) 
    			&& !g.containsNode(new Node<String>(end))) {
    		result += "unknown character " + start;
    		result += "\n" + "unknown character " + end;
    	} else if (!g.containsNode(new Node<String>(start))) {
    		result += "unknown character " + start;
    	} else if (!g.containsNode(new Node<String>(end))) {
    		result += "unknown character " + end;
    	} else {
	    	result = "path from " + start + " to " + end + ":";
	    	Node<String> current = new Node<String>(start);
	    	if (path == null) {
				result += "\n" + "no path found";
	    	} else {
	    		double totalCost = 0;
		    	for (Edge<String,Double> e: path) {
		    		if (!e.getChild().equals(current)) {
						result += "\n" + current.getData() + " to " + 
									e.getChild().getData() + " with weight " + 
								    String.format("%.3f",e.getLabel());
						totalCost += e.getLabel();
						current = e.getChild();
		    		}
				}
		    	result += "\n" + "total cost: " + String.format("%.3f",totalCost);
	    	}
    	}
    	output.println(result);
    }

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }
        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}

