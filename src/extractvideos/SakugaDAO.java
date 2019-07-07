/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extractvideos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author audreyazura
 */
public class SakugaDAO
{
    private final List<Video> videoList = new ArrayList<>();
    
    public SakugaDAO(/*file address*/)
    {
	//create each video in list from file
    }
    
    public List<Video> getVideoList()
    {
	return videoList;
    }
    
}
