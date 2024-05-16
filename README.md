Конфиг файл в проекте - test.json
```
{
  "passengers": "4", 
  "floors": "15",
  "elevators": "3",
  "outputDelayInMs": "1000",
  "generationLowerBoundDelay": "1",
  "generationUpperBoundDelay": "2",
  "numberOfStages": "5"
}
```
passengers - Количество пассажирова
floors - количество этажей
elevators - количество лифтов
outputDelayInMs задержка до вывода (Технически это время между событиями в симуляции)
generationLowerBoundDelay - Минимальное время между генерацией пассажирова в секундах
generationUpperBoundDelay - Максимальное время между генерацией пассажиров в секундах
т.е условно Параметры 1 и 2 означают, что новый пассажир будет генерироваться раз каждые 1-2 секунды.
numberOfStages - Количество стадий в итерации. За 1 стадию лифт может сдвинуться на 1 этаж в нужную сторону и подобрать попутчиков/цель назначения, если тот находится на текущем этаже.
