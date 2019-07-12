/*
 * Copyright (C) 2019 audreyazura
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
package extractvideos;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 *
 * @author audreyazura
 */
public class MissingCutFormatter extends Formatter
{
    private static String m_format = "[%1$tR %1td/%1$tm/%1$tY] %2$s : %3$s";
    
    @Override
    public synchronized String format (LogRecord p_logInfo)
    {
	return String.format(m_format, p_logInfo.getMillis(), p_logInfo.getLevel(), p_logInfo.getMessage());
    }
}
