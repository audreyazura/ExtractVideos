/*
 * Copyright (C) 2019 audreyazura
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package extractvideos_dlappli;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class representing a given video from the database file
 * @author audreyazura
 */
public class Video
{
    private final String m_videoID;
    private final URL m_link;
    
    /**
     * Construct the video from the elements found the in the sakuga base file
     * @param	p_authors   the list of the animator that worked on that video
     * @param	p_anime	    the anime the video is extracted from
     * @param	p_page	    the URL of the page the video can be found on
     * @throws	IOException
     * @throws	MalformedURLException
     */
    public Video (String p_authors, String p_anime, URL p_page) throws MalformedURLException, IOException
    {
	m_videoID = p_authors + " (" + p_anime + ")";
	m_link = findLink(p_page);
    }
    
    /**
     * If the page given as parameters is a sakugabooru page, find the link 
     * toward the video in this page and return it.
     * <p>
     * If the link passed is another kind of link, we test if it is a direct to
     * a video. If it is, the function return said link, otherwise, the returned
     * link is null and the absence of the video is logged.
     * @param	p_link	the link of the page sent to the method
     * @return		the direct link toward the video
     * @throws	IOException
     * @throws	MalformedURLException
     */
    private URL findLink (URL p_link) throws MalformedURLException, IOException
    {
	URL videoLink = null;
	
	//if the passed url is hosted on sakugabooru, then it is not a direct link to the video. So, we download the page and find the video link in it.
	if (p_link.getHost().equalsIgnoreCase("www.sakugabooru.com"))
	{
	    try
	    {
		BufferedReader booruPage = new BufferedReader(new InputStreamReader(p_link.openStream()));
		Pattern videoLinkPatttern = Pattern.compile("https://sakugabooru.com/data/[a-z0-9]*.(m|w)(p|e)(4|bm)");
	
		String dataLine;
		boolean foundAddress = false;
		while (((dataLine = booruPage.readLine()) != null) && !(foundAddress))
		{
		    Matcher linkMatcher = videoLinkPatttern.matcher(dataLine);
		    if (foundAddress = linkMatcher.find())
		    {
			videoLink = new URL(linkMatcher.group());
		    }
		}

		booruPage.close();
		
	    } 
	    catch (FileNotFoundException err)
	    {
//		ExtractVideos_DlAppli.cutInfoLogger.info("No file found for the cut " + m_videoID + " at link: " + p_link);
		ExtractVideos_DlAppli.cutInfoLogger.info(p_link + "\tFichier non trouve : " + m_videoID);
	    }
	} 
	else //the video link is passed as a non-booru link
	{
	    Matcher videoExtensionMatcher = Pattern.compile("(m|w)(p|e)(4|bm)").matcher(p_link.toString());
	    
	    //if the link passed as a parameter is in fact a direct link to a video, it is initialised as it. Otherwise, null is returned.
	    if (videoExtensionMatcher.find())
	    {
		videoLink = p_link;
	    }
	    else
	    {
		ExtractVideos_DlAppli.cutInfoLogger.info(p_link + "\tFichier non trouve : " + m_videoID);
	    }
	}
	
	return videoLink;
    }
    
     /**
     * Find the extension of the video and download it in the folder passed as
     * a parameter, with the indexed passed.
     * @param	p_Folder	the folder in which the video should be downloaded
     * @param	p_videoIndex	the formatted index of the video. Used to correctly name the video file
     * @throws IOException
     */
    public void downloadVideo (String p_Folder, String p_videoIndex) throws IOException
    {
	
	Matcher fileTypeMatcher = Pattern.compile("(m|w)(p|e)(4|bm)").matcher(m_link.getPath());

	//if there is no file extension in the video link, there is a problem, and we raise an extension if it happens
	if (fileTypeMatcher.find())
	{
	    String fileLink = p_Folder + "/" + p_videoIndex + " - " + m_videoID + "." + fileTypeMatcher.group();

	    System.out.println("Downloading " + fileLink);
	    
	    try
	    {
		InputStream videoStream = m_link.openStream();
		Files.copy(videoStream, new File(fileLink).toPath());
		videoStream.close();
	    } 
	    catch (FileNotFoundException notFoundErr)
	    {
		ExtractVideos_DlAppli.cutInfoLogger.info(m_link.toString() + "\tFichier non trouve : " + m_videoID);
	    }	
	}
	else
	{
	    ExtractVideos_DlAppli.cutInfoLogger.info(m_link.toString() + "\tL'extension de la video ne peut etre determine");
//	    throw new IOException("Video type cannot be found in link:" + m_link.toString());
	}
    }
    
    /**
     * Create the subtitle file of the video. Usually, you want it in the same
     * folder as the video, with the same index.
     * @param	p_Folder	the folder in which the video should be downloaded
     * @param	p_videoIndex	the formatted index of the video. Used to correctly name the video file
     * @throws	IOException
     */
    public void makeSub (String p_Folder, String p_videoIndex) throws IOException
    {
	BufferedWriter subBuffer = new BufferedWriter(new FileWriter(p_Folder + "/" + p_videoIndex + " - " + m_videoID + ".srt"));
	
	subBuffer.write("1");
	subBuffer.newLine();
	subBuffer.write("00:00:00,0 --> 99:00:00,0");
	subBuffer.newLine();
	subBuffer.write(m_videoID);
	
	subBuffer.flush();
	subBuffer.close();

    }
    
    /**
     * Tell if the program should try to download the video. It is not worth
     * trying to download if there are no link to the video file.
     * @return	true if the video should be downloaded, false otherwise
     */
    public boolean toDownload ()
    {
	return (m_link != null);
    }
}
