/**
 * An example mapping for the Joystick.
 * @author alexdborn
 * @version 1.0
 */
public enum JoystickMap implements Mappable{
	/* ========= */
	/* CONSTANTS */
	/* ========= */
	PNUMATIC_DOORS("Doors", 0),
	PNUMATIC_PUSH("Pusher", 1),
	PNUMATIC_FLIP("Flipper", 2);

	/* =============== */
	/* CLASS VARIABLES */
	/* =============== */
	private static final int PORTMAPID = 0; // First portmap set
	private final String name; // Name of object manipulated by the port
	private final int port; // Port integer

	/* =========== */
	/* CONSTRUCTOR */
	/* =========== */

	/**
	 * Constructs joystick based on name and port.
	 *
	 * @param name String - Name of object manipulated by the port
	 * @param port Int - Integer mapping to port
	 */
	JoystickMap(String name, int port){
		this.name = name;
		this.port = port;
	}

	/* ======= */
	/* METHODS */
	/* ======= */
	public String toString(){
		return "ID: " + PORTMAPID + " | " + name + ": " + port;
	}

	/**
	 * Get name.
	 *
	 * @return name as String.
	 */
	@Override public String getName() {
	    return name;
	}

	/**
	 * Get port.
	 *
	 * @return port as int.
	 */
	@Override public int getPort() {
	    return port;
	}

	/**
	 * Get portmap ID.
	 *
	 * @return PORTMAP as int
	 */
	@Override public int getPortmapId(){
		return PORTMAPID;
	}
}
