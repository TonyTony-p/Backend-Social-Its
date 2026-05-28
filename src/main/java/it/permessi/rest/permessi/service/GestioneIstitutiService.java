package it.permessi.rest.permessi.service;

import it.permessi.rest.permessi.dto.IstitutoDto;
import it.permessi.rest.permessi.dto.IstitutoFormDto;
import it.permessi.rest.permessi.entity.Istituto;
import it.permessi.rest.permessi.repository.ClasseCorsoRepository;
import it.permessi.rest.permessi.repository.IstitutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GestioneIstitutiService {

    @Autowired private IstitutoRepository istitutoRepo;
    @Autowired private ClasseCorsoRepository classeRepo;

    @Transactional(readOnly = true)
    public List<IstitutoDto> listaTutti() {
        return istitutoRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public IstitutoDto dettaglio(Long id) {
        return toDto(findOrThrow(id));
    }

    @Transactional
    public IstitutoDto crea(IstitutoFormDto form) {
        Istituto i = new Istituto();
        i.setNome(form.getNome());
        i.setDescrizione(form.getDescrizione());
        i.setCitta(form.getCitta());
        i.setUrl(form.getUrl());
        return toDto(istitutoRepo.save(i));
    }

    @Transactional
    public IstitutoDto aggiorna(Long id, IstitutoFormDto form) {
        Istituto i = findOrThrow(id);
        i.setNome(form.getNome());
        i.setDescrizione(form.getDescrizione());
        i.setCitta(form.getCitta());
        i.setUrl(form.getUrl());
        return toDto(istitutoRepo.save(i));
    }

    @Transactional
    public void elimina(Long id) {
        istitutoRepo.delete(findOrThrow(id));
    }

    private Istituto findOrThrow(Long id) {
        return istitutoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Istituto non trovato"));
    }

    private IstitutoDto toDto(Istituto i) {
        IstitutoDto dto = new IstitutoDto();
        dto.setId(i.getId());
        dto.setNome(i.getNome());
        dto.setDescrizione(i.getDescrizione());
        dto.setCitta(i.getCitta());
        dto.setUrl(i.getUrl());
        dto.setNumeroClassi(classeRepo.countByIstituto_Id(i.getId()));
        dto.setCreatedAt(i.getCreatedAt());
        return dto;
    }
}
