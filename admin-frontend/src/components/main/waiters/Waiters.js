import React, {Component} from 'react';
import './Waiters.css'
import {WaiterInfo} from "./WaiterInfo";
import {Button} from "@material-ui/core";

export class Waiters extends Component {
    constructor(props) {
        super(props);
        this.state = {}
    }

    loadWaiters() {
        // TODO
        return [
            {
                id: 1,
                name: 'Test Testovich',
                rating: 3.2,
                tips_total: 10000,
                card_number: '1234 5678 8765 4321'
            },
            {
                id: 2,
                name: 'Testinia Testovna',
                rating: 4.0,
                tips_total: 20000,
                card_number: null
            }
        ]
    }

    render() {
        return (
            <div className='waiters-main'>
                <div className='waiters-wrapper'>
                    <div className='waiters-add-button'>
                        <Button variant='raised'
                                onClick={() => alert('add waiter is wip')}>
                            Добавить официанта
                        </Button>
                    </div>
                    <div className='waiters-list'>
                        {this.loadWaiters().map(waiter =>
                            <WaiterInfo waiter={waiter}/>
                        )}
                    </div>
                </div>
            </div>
        )
    }
}