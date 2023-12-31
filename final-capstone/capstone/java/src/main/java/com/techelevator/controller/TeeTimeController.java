package com.techelevator.controller;

import com.techelevator.dao.TeeTimeDao;
import com.techelevator.model.TeeTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("isAuthenticated()")
@CrossOrigin
@RestController
@RequestMapping("/teetimes")
public class TeeTimeController {


    @Autowired
    private TeeTimeDao teeTimeDao;

    @GetMapping("/{teeTimeId}")
    public TeeTime getTeeTimeById(@PathVariable int teeTimeId) {
        return teeTimeDao.findById(teeTimeId);
    }

    @GetMapping("/upcoming/league/{leagueId}")
    public List<TeeTime> getUpcomingTeeTimesByLeague(@PathVariable("leagueId") int leagueId) {
        return teeTimeDao.getUpcomingTeeTimesByLeague(leagueId);
    }

    @GetMapping("/past/league/{leagueId}")
    public List<TeeTime> getPastTeeTimesByLeague(@PathVariable("leagueId") int leagueId) {
        return teeTimeDao.getPastTeeTimeByLeague(leagueId);
    }


    @GetMapping
    public List<TeeTime> getAllTeeTimes() {
        return teeTimeDao.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public int createTeeTime(@RequestBody TeeTime teeTime) {
        TeeTime savedTeeTime = teeTimeDao.save(teeTime);
        return savedTeeTime.getTeeTimeId();
    }

    @PutMapping("/{teeTimeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTeeTime(@PathVariable int teeTimeId, @RequestBody TeeTime teeTime) {
        teeTime.setTeeTimeId(teeTimeId);
        teeTimeDao.update(teeTime);
    }

    @DeleteMapping("/{teeTimeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTeeTime(@PathVariable int teeTimeId) {
        TeeTime teeTime = teeTimeDao.findById(teeTimeId);
        if (teeTime != null) {
            teeTimeDao.delete(teeTime);
        }
    }
    @GetMapping("/{username}/leagueId")
    public int getLeagueIdByUsername(@PathVariable("username") String username) {
        return teeTimeDao.getLeagueIdByUsername(username);
    }

    @GetMapping("/course/{courseId}")
    public List<TeeTime> getTeeTimesByCourse(@PathVariable int courseId) {
        return teeTimeDao.findByCourse(courseId);
    }

    @GetMapping("/{teeTimeId}/leagueid")
    public int getLeagueIdByTeeTimeId(@PathVariable int teeTimeId) {
            return teeTimeDao.getLeagueIdByTeeTimeId(teeTimeId);

    }
    @GetMapping("/{matchId}/teetimeid")
    public int getTeeTimeIdByMatchId(@PathVariable int matchId) {
        return teeTimeDao.getTeeTimeIdByMatchId(matchId);
    }

}
