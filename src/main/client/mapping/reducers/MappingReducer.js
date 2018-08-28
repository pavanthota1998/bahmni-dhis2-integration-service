export function allTables(state = [], action = {}) {
  switch (action.type) {
    case 'allTables':
      return action.allTables;
    default:
      return state;
  }
}

export function filteredInstanceTables(state = [], action = {}) {
  switch (action.type) {
    case 'filteredInstanceTables':
      return action.filteredInstanceTables;
    default:
      return state;
  }
}

export function filteredEnrollmentTables(state = [], action = {}) {
  switch (action.type) {
    case 'filteredEnrollmentTables':
      return action.filteredEnrollmentTables;
    default:
      return state;
  }
}

export function selectedInstanceTable(state = '', action = {}) {
  switch (action.type) {
    case 'selectedInstanceTable':
      return action.selectedInstanceTable;
    default:
      return state;
  }
}

export function selectedEnrollmentsTable(state = '', action = {}) {
  switch (action.type) {
    case 'selectedEnrollmentsTable':
      return action.selectedEnrollmentsTable;
    default:
      return state;
  }
}

export function selectedInstanceTableColumns(state = [], action = {}) {
  switch (action.type) {
    case 'selectedInstanceTableColumns':
      return action.selectedInstanceTableColumns;
    default:
      return state;
  }
}

export function selectedEnrollmentTableColumns(state = [], action = {}) {
  switch (action.type) {
    case 'selectedEnrollmentTableColumns':
      return action.selectedEnrollmentTableColumns;
    default:
      return state;
  }
}

export function allMappingNames(state = [], action = {}){
    switch (action.type) {
        case 'allMappings':
            return action.allMappings;
        case 'addNewMapping':
            return state.concat(action.mappingName);
        default:
            return state;
    }
}

export function currentMapping(state = '', action = {}) {
    switch (action.type) {
        case 'currentMapping':
            return action.mappingName;
        default:
            return state;
    }
}

export function mappingJson(state = {instance:{}, enrollments:{}}, action = {}) {
    switch (action.type) {
        case 'mappingJson':
            return action.mappingJson;
        default:
            return state;
    }
}
