/**
 * Interface used so all mappings are cross-compatable with assignment methods.
 * @author alexdborn
 * @version 1.0
 */

public interface Mappable {
	/* ========= */
	/* ACCESSORS */
	/* ========= */

	/**
	 * Get port.
	 *
	 * @return port - Integer repersenting Port of object
	 */
	public int getPort();

	/**
	 * Get name.
	 *
	 * @return name - String repersenting name of object controlled by the port
	 */
	public String getName();

	/**
	 * Get id.
	 *
	 * @return id  - Integer id of portmap. Used for identifying enum.
	 */
	public int getPortmapId();
}
