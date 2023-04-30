/*
 * Copyright (C) 2020 audreyazura
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
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
package extractvideo_GUI;

import javafx.stage.Stage;

/**
 *
 * @author audreyazura
 */
interface WindowsCall
{
    void loadMainWindow(String p_address);
    void popupInfo(String infoMessage, String fileAddress, boolean redirect);
    void initiateDlScene();
    Stage getMainStage();
}
