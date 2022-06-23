package ru.itis.azat.ojs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.azat.ojs.model.ActionInfo;
import ru.itis.azat.ojs.model.ActionInfoContext;
import ru.itis.azat.ojs.model.ActionInfoUser;
import ru.itis.azat.ojs.repository.ActionInfoContextRepository;
import ru.itis.azat.ojs.repository.ActionInfoRepository;
import ru.itis.azat.ojs.repository.ActionInfoUserRepository;
import ru.itis.azat.ojs.transfer.ActionInfoContextDto;
import ru.itis.azat.ojs.transfer.ActionInfoDto;
import ru.itis.azat.ojs.transfer.ActionInfoUserDto;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ActionInfoServiceImpl implements ActionInfoService {

    @Autowired
    private ActionInfoRepository actionInfoRepository;

    @Autowired
    private ActionInfoUserRepository actionInfoUserRepository;

    @Autowired
    private ActionInfoContextRepository actionInfoContextRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    @Transactional
    public void addAll(List<ActionInfoDto> actionInfoDtoList) {
        actionInfoDtoList.forEach(this::saveActionInfo);
    }

    public void saveActionInfo(ActionInfoDto actionInfoDto) {
        try {

            ActionInfo actionInfo = new ActionInfo();
            actionInfo.update(actionInfoDto);
            actionInfo.setUser(getAndUpdateActionInfoUser(actionInfoDto.getUser()));
            actionInfo.setContext(getAndUpdateActionInfoContext(actionInfoDto.getContext()));
            actionInfo.setOwner(authenticationService.getCurrentUser());
            actionInfoRepository.save(actionInfo);
        } catch (Exception e) {
            int debug = 1;
        }
    }

    public ActionInfoUser getAndUpdateActionInfoUser(ActionInfoUserDto actionInfoUserDto) {
        if (actionInfoUserDto == null) {
            return null;
        }

        ActionInfoUser actionInfoUser = actionInfoUserRepository.findFirstByOjsIdAndUsername(actionInfoUserDto.getId(), actionInfoUserDto.getUsername())
                .orElse(new ActionInfoUser());

        actionInfoUser.update(actionInfoUserDto);
        return actionInfoUser;
    }

    public ActionInfoContext getAndUpdateActionInfoContext(ActionInfoContextDto actionInfoContextDto) {
        if (actionInfoContextDto == null) {
            return null;
        }

        ActionInfoContext actionInfoContext = actionInfoContextRepository.findFirstByOjsIdAndUrlPath(actionInfoContextDto.getId(), actionInfoContextDto.getUrlPath())
                .orElse(new ActionInfoContext());

        actionInfoContext.update(actionInfoContextDto);
        return actionInfoContext;
    }
}
