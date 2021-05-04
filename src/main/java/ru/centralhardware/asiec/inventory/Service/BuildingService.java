package ru.centralhardware.asiec.inventory.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor=Exception.class)
public class BuildingService {
}
