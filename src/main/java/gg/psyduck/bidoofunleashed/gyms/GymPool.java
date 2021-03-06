package gg.psyduck.bidoofunleashed.gyms;

import com.google.common.collect.Lists;
import gg.psyduck.bidoofunleashed.api.pixelmon.specs.BU3PokemonSpec;
import gg.psyduck.bidoofunleashed.api.battlables.ShowdownImporter;
import lombok.Getter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Getter
public class GymPool {

	private final File pool;
    private transient List<BU3PokemonSpec> team = Lists.newArrayList();

    public GymPool(File pool) {
    	this.pool = pool;
    	this.createFiles(pool);
    }

    public GymPool init() {
    	if(pool == null) {
    		return this;
	    }

    	this.team = ShowdownImporter.importFromFile(pool);
    	return this;
    }

    private void createFiles(File path) {
	    if(!path.exists()) {
		    path.getParentFile().mkdirs();
		    try {
			    path.createNewFile();
		    } catch (IOException ignored) {}
	    }
    }

	public void append(String export) {
		try {
			FileWriter fw = new FileWriter(pool, true);
			fw.append(export);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
