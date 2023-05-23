package Music;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import Music.commands.RepeatCommand;

public class Queue {
	private List<AudioTrack> queuelist;
	private MusicController controller;
	private int lastAddedTrackIndex;

	public Queue(MusicController controller) {
		this.setController(controller);
		this.setQueuelist(new ArrayList<AudioTrack>());
	}

	public boolean next() {
		if (this.queuelist.size() >= 1) {
		    
			AudioTrack trackNext = queuelist.remove(0);
			
			if (trackNext != null) {
				this.controller.getPlayer().playTrack(trackNext);
				
				return true;
			}
		}
		return false;
	}
	
	public boolean hasNext() {
	    if(this.queuelist.size() > 0) return true;
	    return false;
	}

	public void addTrackToQueueNext(AudioTrack track) {
		this.queuelist.add(0, track);
		lastAddedTrackIndex = 0;
		if (controller.getPlayer().getPlayingTrack() == null) {
			next();
		}
	}

	public void addTrackToQueue(AudioTrack track) {
		this.queuelist.add(track);
		lastAddedTrackIndex = queuelist.size()-1;
		if (controller.getPlayer().getPlayingTrack() == null) {
		    System.out.println("addedTrack");
			next();
		}

	}

	public MusicController getController() {
		return controller;
	}

	public void setController(MusicController controller) {
		this.controller = controller;
	}

	public List<AudioTrack> getQueuelist() {
		return queuelist;
	}

	public void setQueuelist(List<AudioTrack> queuelist) {
		this.queuelist = queuelist;
	}
	
	public int getLastAddedTrackIndex() {
	    return lastAddedTrackIndex;
	}
	
	public void shuffel() {
		Collections.shuffle(queuelist);
	}

	public void clearQueueList() {
		queuelist.removeAll(queuelist);
	}

	public int getQueueSize() {
		return this.queuelist.size();
	}
	
	
	public AudioTrack removeLast() {
	    return queuelist.remove(lastAddedTrackIndex);
	}
	
	
	
	
}
