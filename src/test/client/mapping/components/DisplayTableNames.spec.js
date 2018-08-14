import 'jsdom-global/register';
import React from 'react';
import thunkMiddleware from 'redux-thunk';
import {
  configure, mount
} from 'enzyme';
import { applyMiddleware, createStore } from 'redux';
import { Provider } from 'react-redux';
import Adapter from 'enzyme-adapter-react-16';
import sinon from 'sinon';
import * as MappingActions from '../../../../main/client/mapping/actions/MappingActions';
import DisplayTableNames from '../../../../main/client/mapping/components/DisplayTableNames';

configure({ adapter: new Adapter() });


describe('DisplayTableNames', () => {
  let rendered;

  beforeEach(() => {
    const store = createStore(() => ({
      filteredTables: ['pat_identifier', 'program']
    }), applyMiddleware(thunkMiddleware));

    rendered = mount(
      <Provider store={store}>
        <DisplayTableNames dispatch={() => {
        }}
        />
      </Provider>
    );
  });

  it('should have ul element with type none', () => {
    expect(rendered.find('ul')).toHaveLength(1);
    expect(rendered.find('ul').props().type).toEqual('none');
  });

  describe('#li elements', () => {
    it('should have two li elements', () => {
      const liElements = rendered.find('li');
      expect(liElements).toHaveLength(2);
    });

    it('should have filteredTables as li elements', () => {
      const liElements = rendered.find('li');
      expect(liElements.first().text()).toEqual('pat_identifier');
      expect(liElements.at(1).text()).toEqual('program');
    });

    it('should dispatch selectedTable and mappingJson on click on li element', () => {
      const sandBox = sinon.createSandbox();

        let mappingActions = sandBox.mock(MappingActions);
        const selectedTableMock = mappingActions.expects('selectedTable')
        .withArgs('pat_identifier')
        .returns({
          type: 'selectedTable',
          selectedTable: 'pat_identifier'
        });

        let mappingJson = mappingActions.expects('mappingJson')
            .returns({ type: '' });

      rendered.find('li').first().simulate('click', { target: { dataset: { tableName: 'pat_identifier' } } });

      selectedTableMock.verify();
      mappingJson.verify();

      sandBox.restore();
    });
  });
});