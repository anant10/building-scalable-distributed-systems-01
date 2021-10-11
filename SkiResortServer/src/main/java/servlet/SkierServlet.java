package servlet;

import com.google.gson.Gson;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;

import model.LiftRide;
import model.ResponseMessage;
import model.SeasonInfo;
import model.SkierVertical;

@WebServlet(name = "servlet.SkierServlet", urlPatterns = "/skiers/*")
public class SkierServlet extends javax.servlet.http.HttpServlet {
  private Gson gson  = new Gson();
  private static final int DAY_ID_MIN = 1;
  private static final int DAY_ID_MAX = 366;
  private static final String SEASONS_PARAMETER = "seasons";
  private static final String DAYS_PARAMETER = "days";
  private static final String SKIERS_PARAMETER = "skiers";
  private static final String VERTICAL_PARAMETER = "vertical";

  protected void doPost(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse res) throws javax.servlet.ServletException, IOException {
    res.setContentType("application/json");
    res.setCharacterEncoding("UTF-8");

    String urlPath = req.getPathInfo();

    // check we have a URL!
    if (urlPath == null || urlPath.isEmpty()) {
      res.setStatus(HttpServletResponse.SC_NOT_FOUND);
      ResponseMessage responseMessage = new ResponseMessage("Invalid Parameters");
      res.getWriter().write(gson.toJson(responseMessage));
      return;
    }

    String[] urlParts = urlPath.split("/");
    // and now validate url path and return the response status code
    // (and maybe also some value if input is valid)

    if (!isUrlValid(urlParts)) {
      ResponseMessage responseMessage = new ResponseMessage("Invalid Parameters");
      res.getWriter().write(gson.toJson(responseMessage));
      res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    } else {
//      LiftRide liftRide = new LiftRide(12,1234);
//      res.getWriter().write(gson.toJson(liftRide));
      res.setStatus(HttpServletResponse.SC_OK);
    }
  }

  protected void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse res) throws javax.servlet.ServletException, IOException {
    res.setContentType("application/json");
    res.setCharacterEncoding("UTF-8");
    String urlPath = req.getPathInfo();

    // check we have a URL!
    if (urlPath == null || urlPath.isEmpty()) {
      ResponseMessage responseMessage = new ResponseMessage("Invalid Parameters");
      res.getWriter().write(gson.toJson(responseMessage));
      res.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return;
    }

    String[] urlParts = urlPath.split("/");
    // and now validate url path and return the response status code
    // (and maybe also some value if input is valid)

    if (!isUrlValid(urlParts)) {
      ResponseMessage responseMessage = new ResponseMessage("Invalid Parameters");
      res.getWriter().write(gson.toJson(responseMessage));
      res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    } else {
      res.setStatus(HttpServletResponse.SC_OK);
      if(urlParts.length==3){
        SkierVertical skierVertical = new SkierVertical();
        skierVertical.add(new SeasonInfo("season", 0));
        res.getWriter().write(gson.toJson(skierVertical));
      }
      else{
        res.getWriter().write(gson.toJson(34507));
      }
    }
  }

  private boolean isUrlValid(String[] urlPath) {
    // TODO: validate the request url path according to the API spec
    // urlPath  = "/1/seasons/2019/day/1/skier/123"
    // urlParts = [, 1, seasons, 2019, day, 1, skier, 123]
    if (urlPath.length == 8) {
      try {
        for (int i = 1; i < urlPath.length; i += 2) {
          Integer.parseInt(urlPath[i]);
        }
        return (urlPath[3].length() == 4
                && Integer.valueOf(urlPath[5]) >= DAY_ID_MIN
                && Integer.valueOf(urlPath[5]) <= DAY_ID_MAX
                && urlPath[2].equals(SEASONS_PARAMETER)
                && urlPath[4].equals(DAYS_PARAMETER)
                && urlPath[6].equals(SKIERS_PARAMETER));
      } catch (Exception e) {
        return false;
      }
    } else if (urlPath.length == 3) {
      try {
        Integer.parseInt(urlPath[1]);
        return (urlPath[2].equals(VERTICAL_PARAMETER));
      } catch (Exception e) {
        return false;
      }
    }
    return false;
  }
}
