package aaron.game.pokemonatb.manager;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

public class LuaManager {
	
	LuaManager luaManager;
	
	Globals globals = JsePlatform.standardGlobals();
	LuaValue chunk = globals.load("print 'hello, world'");
	//chunk.call();
	
	public LuaManager(){
		
	}
	
	public LuaManager getInstance(){
		if(luaManager == null){
			luaManager = new LuaManager();
		}
		return luaManager;
	}
}
