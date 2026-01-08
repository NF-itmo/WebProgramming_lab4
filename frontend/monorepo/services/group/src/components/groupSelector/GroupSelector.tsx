import { useEffect, useState } from 'react';
import styles from './GroupSelector.module.css';

type props = {
    onGroupCreateHandler: (groupName: string) => void,
    onGroupSelectionHandler: (groupId: number) => void,
    groups: groups,
    currentGroupId?: number
}

type groups = Record<number, string>

const GroupSelector = (
    {
        onGroupCreateHandler,
        onGroupSelectionHandler,
        groups,
        currentGroupId
    }: props
) => {
    const [loading, setLoading] = useState<boolean>(false);
    const [showCreateForm, setShowCreateForm] = useState<boolean>(false);
    const [newGroupName, setNewGroupName] = useState<string>('');

    const handleCreateGroup = () => {
        setLoading(true);
        onGroupCreateHandler(
            newGroupName
        );
        setLoading(false);
    }
    const handleGroupSelection = (groupId: number) => {
        return () => {
            onGroupSelectionHandler(groupId)
        }
    }

    return (
        <div className={styles.groupSelector}>
            <div className={styles.header}>
                <h3>Groups</h3>
                <button
                    className={styles.createBtn}
                    onClick={() => setShowCreateForm(!showCreateForm)}
                    disabled={loading}
                >
                    {showCreateForm ? 'Cancel' : 'Create'}
                </button>
            </div>

            {showCreateForm && (
                <div className={styles.createForm}>
                    <input
                        type="text"
                        placeholder="Enter group name"
                        value={newGroupName}
                        onChange={(e) => setNewGroupName(e.target.value)}
                        className={styles.input}
                        disabled={loading}
                    />
                    <button
                        onClick={handleCreateGroup}
                        disabled={loading || !newGroupName.trim()}
                        className={styles.submitBtn}
                    >
                        {loading ? 'Creating...' : 'Create Group'}
                    </button>
                </div>
            )}

            <div className={styles.groupsList}>
                {Object.keys(groups).length === 0 ? (
                    <p className={styles.noGroups}>No groups yet. Create one to start!</p>
                ) : (
                    Object.entries(groups).map(([groupId, name]) => (
                        <div
                            key={groupId}
                            className={`${styles.groupItem} ${
                                currentGroupId === Number(groupId) ? styles.active : ''
                            }`}
                            onClick={handleGroupSelection(Number(groupId))}
                        >
                            <span className={styles.name}>{name}</span>
                        </div>
                    ))
                )}
            </div>
        </div>
    );
};

export default GroupSelector;
