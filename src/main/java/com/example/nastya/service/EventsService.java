package com.example.nastya.service;

import com.example.nastya.entity.Events;
import com.example.nastya.entity.EventsAthletes;
import com.example.nastya.repository.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class EventsService {
    @Autowired
    private EventsRepository eventsRepository;

    public Events addEvent(Events event) {
        return eventsRepository.saveAndFlush(event);
    }

    public void delete(Long id) {
        eventsRepository.deleteById(id);
    }

    public Events getByName(String name) {
        return eventsRepository.findByName(name);
    }

    public Events editEvent(Events event) {
        return eventsRepository.saveAndFlush(event);
    }

    public Optional<Events> findById(long id) {
        return eventsRepository.findById(id);
    }

    public List<Events> getAll() {
        return eventsRepository.findAll();
    }

    public EventsAthletes addAthleteInEvent(long idEvents, long idAthletes) {
        eventsRepository.addAthleteInEvent(idEvents, idAthletes);
        return new EventsAthletes(idEvents, idAthletes);
    }

    public EventsAthletes deleteAthleteFromEvent(long idEvents, long idAthletes) {
        eventsRepository.deleteAthleteFromEvent(idEvents, idAthletes);
        return new EventsAthletes(idEvents, idAthletes);
    }

    public Events getById(long id) {
        return eventsRepository.getById(id);
    }

    public List<Events> searchEvents(String search) {
        List<Events> list = new ArrayList();
        List<Events> events = eventsRepository.findAll();

        events.forEach(event -> {
            AtomicBoolean flag = new AtomicBoolean(false);
            event.getAthletesSet().forEach(athlete -> {
                if(athlete.getName().toLowerCase().contains(search.toLowerCase())) {
                    flag.set(true);
                }
            });
            if(event.getName().toLowerCase().contains(search.toLowerCase()) ||
            event.getDescription().toLowerCase().contains(search.toLowerCase()) ||
            event.getDate().toString().contains(search.toLowerCase()) ||

            event.getRegion().toLowerCase().contains(search.toLowerCase()) || flag.get()) list.add(event);
        });

        return list;
    }
}
