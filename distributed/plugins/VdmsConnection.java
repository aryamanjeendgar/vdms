import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//http://commons.apache.org/proper/commons-codec/
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.DecoderException;

public abstract class VdmsConnection
{
    enum FunctionID { ConfigManager, DataFlowManager, ReplicationPlugin, FilterPlugin, AutoTaskPlugin};
    
    protected int registrationId;
    protected int functionId;
    protected String hostName;
    protected int hostPort;
    protected VdmsTransaction initSequence;
    protected int initSequenceSizeMultiplier;
    
    VdmsConnection()
    {
        
    }
    
    VdmsConnection(String initString)
    {
        try
        {
            JSONParser connectionParser = new JSONParser();
            JSONObject connection = (JSONObject) connectionParser.parse(initString);
            registrationId = ((Long) connection.get("RegistrationId")).intValue();
            functionId = ((Long) connection.get("FunctionId")).intValue();
            hostName  = ((String) connection.get("HostName"));
            hostPort = ((Long) connection.get("HostPort")).intValue();
            String tmpString = (String) connection.get("InitBytes");
            initSequence = new VdmsTransaction( tmpString.length(), Hex.decodeHex(tmpString));
            initSequenceSizeMultiplier = ((Long) connection.get("InitBytesMultiplier")).intValue();
        }  
        catch(ParseException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
        catch(DecoderException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }

    }

    public int GetegistrationRId()
    {
        return registrationId;
    }
    
    public int GetFunctionId()
    {
        return functionId;
    }
    
    public String GetHostName()
    {
        return hostName;
    }
    
    public int GetHostPort()
    {
        return hostPort;
    }
    
    public VdmsTransaction GetInitSequence()
    {
        return initSequence;
    }
    
    public abstract void Close();

    public abstract void WriteInitMessage();
    
    public abstract void Write(VdmsTransaction outMessage);
    
    public abstract void WriteExtended(VdmsTransaction outMessage);

    public abstract VdmsTransaction Read();

    public abstract VdmsTransaction ReadExtended();
    
}