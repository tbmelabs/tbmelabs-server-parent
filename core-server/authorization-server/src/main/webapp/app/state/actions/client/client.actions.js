// @flow

import {Action} from 'redux';

import {
  DELETE_CLIENT,
  DELETE_CLIENT_SUCCEED,
  REQUEST_CLIENTS,
  SAVE_CLIENT,
  SAVE_CLIENT_SUCCEED,
  SET_CLIENTS,
  UPDATE_CLIENT,
  UPDATE_CLIENT_SUCCEED
} from './client.types';

import {type ClientState} from '../../reducers/client.reducer';

import {type clientType} from '../../../../common/types/client.type';

export function setClientsAction(page: ClientState): Action {
  return {
    type: SET_CLIENTS,
    payload: page
  };
}

export function requestClientsAction(): Action {
  return {
    type: REQUEST_CLIENTS
  };
}

export function saveClientAction(client: clientType): Action {
  return {
    type: SAVE_CLIENT,
    payload: client
  };
}

export function saveClientSucceedAction(): Action {
  return {
    type: SAVE_CLIENT_SUCCEED
  };
}

export function updateClientAction(client: clientType): Action {
  return {
    type: UPDATE_CLIENT,
    payload: client
  }
}

export function updateClientSucceedAction(): Action {
  return {
    type: UPDATE_CLIENT_SUCCEED
  }
}

export function deleteClientAction(): Action {
  return {
    type: DELETE_CLIENT
  }
}

export function deleteClientSucceedAction(): Action {
  return {
    type: DELETE_CLIENT_SUCCEED
  }
}
